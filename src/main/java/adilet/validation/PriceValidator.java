package adilet.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class PriceValidator implements ConstraintValidator<PriceValidation, BigDecimal> {
    @Override
    public boolean isValid(BigDecimal price, ConstraintValidatorContext context) {
        return price.compareTo(BigDecimal.ZERO) >= 0;
    }
}
