package com.epam.esm.validator;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectDataException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

class TagValidatorTest {

    @Mock
    private Translator translator;
    @InjectMocks
    private TagValidator tagValidator;

//    @Test
//    void testDoesNotThrowWhenTagNameIsValid() {
//        Tag tag = new Tag("tag");
//        assertDoesNotThrow(() -> tagValidator.validTag(tag));
//    }

    @Test
    void testThrowsExceptionWhenNameIsBlank() {
        Tag tag = new Tag("");
        Mockito.when(translator.toLocale(any())).thenReturn("message");
        assertThrows(IncorrectDataException.class, () -> tagValidator.validTag(tag));
    }
}