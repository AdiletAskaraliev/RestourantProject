package adilet.repository;

import adilet.entity.Cheque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface ChequeRepository extends JpaRepository<Cheque, Long> {

    @Query("select avg(c.priceAverage) from Cheque c where c.user.restaurant.id=:restId")
    BigDecimal getAverageSum(Long restId);
}
