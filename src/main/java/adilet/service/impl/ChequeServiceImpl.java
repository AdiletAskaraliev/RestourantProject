package adilet.service.impl;

import adilet.dto.SimpleResponse;
import adilet.dto.request.ChequeRequest;
import adilet.dto.response.ChequeResponse;
import adilet.dto.response.StopListResponse;
import adilet.entity.Cheque;
import adilet.entity.MenuItem;
import adilet.entity.StopList;
import adilet.entity.User;
import adilet.exception.AlreadyExistException;
import adilet.exception.NotFoundException;
import adilet.repository.ChequeRepository;
import adilet.repository.MenuItemRepository;
import adilet.repository.StopListRepository;
import adilet.repository.UserRepository;
import adilet.service.ChequeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChequeServiceImpl implements ChequeService {

    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final StopListRepository stopListRepository;

    @Override
    public SimpleResponse save(ChequeRequest request) {
        BigDecimal count = BigDecimal.ZERO;
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User with id: " + request.getUserId() + " is no exist!"));
        Cheque cheque = new Cheque();
        cheque.setUser(user);
        List<Long> menuItemsId = request.getMenuItemsId();
        for (MenuItem menuItem : menuItemRepository.findAllById(menuItemsId)) {
            List<StopList> stopLists = stopListRepository.findAll();
            for (Long menuItemId:menuItemsId){

                for (StopList stopList : stopLists) {
                    MenuItem menuItem1 = stopList.getMenuItem();
                    if (menuItemId.equals(menuItem1.getId())){
                        throw new AlreadyExistException("MenuItem with " + menuItemId + " is already in stop list!");
                    }
                }
            }
            cheque.addMenuItem(menuItem);
            BigDecimal menuItemPrice = menuItem.getPrice();
            count = count.add(menuItemPrice);
        }
        cheque.setPriceAverage(count);
        cheque.setCreatedAt(LocalDateTime.now());

        int servicePercentageInt = cheque.getUser().getRestaurant().getService();
        BigDecimal servicePercentage = BigDecimal.valueOf(servicePercentageInt);
        BigDecimal result = count.multiply(servicePercentage).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        cheque.setGrandTotal(count.add(result));
        chequeRepository.save(cheque);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Cheque with id: " + cheque.getId() + " is saved!")
                .build();
    }

    @Override
    public List<ChequeResponse> getAll() {
        List<Cheque> cheques = chequeRepository.findAll();
        List<ChequeResponse> chequeResponses = new ArrayList<>();

        for (Cheque cheque : cheques) {
            ChequeResponse chequeResponse = new ChequeResponse();
            chequeResponse.setId(cheque.getId());
            chequeResponse.setFullName(cheque.getUser().getFirstname() + cheque.getUser().getLastname());
            chequeResponse.setItems(cheque.getMenuItems());
            chequeResponse.setAveragePrice(cheque.getPriceAverage());
            chequeResponse.setService(cheque.getUser().getRestaurant().getService());
            chequeResponse.setTotal(cheque.getGrandTotal());
            chequeResponses.add(chequeResponse);
        }
        return chequeResponses;
    }

    @Override
    public ChequeResponse getById(Long id) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() -> new NotFoundException("Cheque with id: " + id + " is no exist!"));
        ChequeResponse chequeResponse = new ChequeResponse();
        chequeResponse.setId(cheque.getId());
        chequeResponse.setFullName(cheque.getUser().getFirstname() + cheque.getUser().getLastname());
        chequeResponse.setItems(cheque.getMenuItems());
        chequeResponse.setAveragePrice(cheque.getPriceAverage());
        chequeResponse.setService(cheque.getUser().getRestaurant().getService());
        chequeResponse.setTotal(cheque.getGrandTotal());
        return new ChequeResponse(
                chequeResponse.getId(),
                chequeResponse.getFullName(),
                chequeResponse.getItems(),
                chequeResponse.getAveragePrice(),
                chequeResponse.getService(),
                chequeResponse.getTotal()
        );
    }

    @Override
    public SimpleResponse update(Long id, ChequeRequest request) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() -> new NotFoundException("Check with id: " + id + " not found!"));
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new NotFoundException("User with id: " + id + " not found!"));
        List<MenuItem> allById = menuItemRepository.findAllById(request.getMenuItemsId());
        cheque.setMenuItems(allById);
        cheque.setUser(user);
        chequeRepository.save(cheque);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK)
                .message("Cheque with id: " + cheque.getId() + " is successfully updated!")
                .build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        chequeRepository.findById(id).orElseThrow(() -> new NotFoundException("Cheque with id: " + id + " is no exist"));
        chequeRepository.deleteById(id);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK)
                .message("Cheque with id: " + id + " is successfully deleted!!")
                .build();
    }

    @Override
    public BigDecimal getAllChequesByUser(Long userId) {
        BigDecimal total = BigDecimal.ZERO;

        for (Cheque cheque : chequeRepository.findAll()) {
            if (cheque.getUser().getId().equals(userId) && cheque.getCreatedAt().toLocalDate().equals(LocalDate.now())) {
                BigDecimal grandTotal = new BigDecimal(String.valueOf(cheque.getGrandTotal()));
                total = total.add(grandTotal);
            }
        }

        return total;
    }


    @Override
    public BigDecimal getAverageSum(Long restId) {
        return chequeRepository.getAverageSum(restId);
    }
}
