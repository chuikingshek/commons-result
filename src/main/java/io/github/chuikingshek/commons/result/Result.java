package io.github.chuikingshek.commons.result;

import org.intellij.lang.annotations.Flow;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;
import java.util.Objects;

public class Result<T, E> {

    private final boolean present;

    private final T value;

    private final E error;

    private Result(final boolean present, final T value, final E error) {
        this.present = present;
        this.value = value;
        this.error = error;
    }

    public static <T1, E1> Result<T1, E1> of(final T1 value) {
        Objects.requireNonNull(value, "value must not be null");
        return new Result<>(true, value, null);
    }

    public static <T1, E1> Result<T1, E1> ofNullable(final T1 value, final E1 error) {
        Objects.requireNonNull(error, "error must not be null");
        return Objects.nonNull(value) ? of(value) : error(error);
    }

    public static <T1, E1> Result<T1, E1> error(final E1 error) {
        Objects.requireNonNull(error, "error must not be null");
        return new Result<>(false, null, error);
    }

    @NotNull
    @Contract(pure = true)
    @Flow(sourceIsContainer = true)
    public T get() {
        if (present) {
            return value;
        }
        throw new NoSuchElementException("No value present");
    }

    @NotNull
    @Contract(pure = true)
    @Flow(sourceIsContainer = true)
    public E getError() {
        if (!present) {
            return error;
        }
        throw new NoSuchElementException("No error present");
    }

    public boolean isPresent() {
        return present;
    }

    public boolean isEmpty() {
        return !present;
    }

    @Override
    public int hashCode() {
        return Objects.hash(present, value, error);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        return obj instanceof Result<?, ?> other
               && present == other.present
               && Objects.equals(value, other.value)
               && Objects.equals(error, other.error);
    }

    @Override
    public String toString() {
        return present ? ("Result[" + value + "]") : ("Error[" + error + "]");
    }

}
