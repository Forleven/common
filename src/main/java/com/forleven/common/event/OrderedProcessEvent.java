package com.forleven.common.event;

import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class OrderedProcessEvent<T extends Serializable> extends ApplicationEvent implements ResolvableTypeProvider {

    private static final int MAX_STEP = 10;

    private static final int FIRST = 1;
    private static final int SECOND = 2;
    private static final int THIRD = 3;
    private static final int FOURTH = 4;
    private static final int FIFTH = 5;
    private static final int SIXTH = 6;
    private static final int SEVENTH = 7;
    private static final int EIGHTH = 8;
    private static final int NINTH = 9;
    private static final int TENTH = 10;

    private T model;

    @Setter(value = AccessLevel.NONE)
    private AtomicInteger atomStep = new AtomicInteger(0);

    public static <T extends Serializable> OrderedProcessEvent<T> start(T model) {
        return new OrderedProcessEvent<>(FIRST, model);
    }

    /**
     * Event reference Generic
     * @see <a href="https://github.com/olivergierke/spring-examples/blob/6a9845831eb9c4d31cb0069255173fb70a2baefe/4.2/src/main/java/example/events/EventsSample.java#L118-L133">Github reference</a>
     */
    public OrderedProcessEvent(T source) {
        this(FIRST, source);
    }

    public OrderedProcessEvent(int step, T source) {
        super(source);

        if (!isValidStep(step)) {
            throw new UnsupportedOperationException(step + " out range 0 and " + MAX_STEP);
        }

        setModel(source);
        atomStep.set(step);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.core.ResolvableTypeProvider#getResolvableType()
     */
    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(source));
    }

    public int getStep() {
        return atomStep.get();
    }

    public Optional<OrderedProcessEvent<T>> nextEvent() {
        return Optional.of(atomStep.get())
                .map(step -> step + 1)
                .filter(this::isValidStep)
                .map(step -> new OrderedProcessEvent<>(step, model));
    }

    private boolean isValidStep(int step) {
        return step >= 0 && step <= MAX_STEP;
    }

    public boolean isFirst() {
        return getStep() == FIRST;
    }

    public boolean isSecond() {
        return getStep() == SECOND;
    }

    public boolean isThird() {
        return getStep() == THIRD;
    }

    public boolean isFourth() {
        return getStep() == FOURTH;
    }

    public boolean isFifth() {
        return getStep() == FIFTH;
    }

    public boolean isSixth() {
        return getStep() == SIXTH;
    }

    public boolean isSeventh() {
        return getStep() == SEVENTH;
    }

    public boolean isEighth() {
        return getStep() == EIGHTH;
    }

    public boolean isNinth() {
        return getStep() == NINTH;
    }

    public boolean isTenth() {
        return getStep() == TENTH;
    }
}