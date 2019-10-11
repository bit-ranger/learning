package com.rainyalley.practice;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import java.util.Set;
import static org.junit.Assert.*;

/**
 * Created by sllx on 6/14/15.
 */
public class ValidatorTest {
    @Test
    public void test(){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Driver driver = new Driver();
        Set<ConstraintViolation<Driver>> groupResult = validator.validate(driver, Driver.DriverCheck.class);
        assertTrue(groupResult.size() > 0);
        Set<ConstraintViolation<Driver>> result = validator.validate(driver);
        assertTrue(result.size() == 0);

    }


    static class Driver{
        static interface DriverCheck{}

        @AssertTrue(groups = DriverCheck.class)
        boolean hasDrivingLicense;
        @Min(value = 18,groups = DriverCheck.class)
        int age = 18;
    }
}

