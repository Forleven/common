package com.forleven.common.event;

import java.io.Serializable;

import lombok.*;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

@Builder
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UpdatingEvent<T extends Serializable> extends ApplicationEvent implements ResolvableTypeProvider {
    private T model;

    /**
     * Event reference Generic
     * @see <a href="https://github.com/olivergierke/spring-examples/blob/6a9845831eb9c4d31cb0069255173fb70a2baefe/4.2/src/main/java/example/events/EventsSample.java#L118-L133">Github reference</a>
     */
    public UpdatingEvent(T source) {
        super(source);
        setModel(source);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.core.ResolvableTypeProvider#getResolvableType()
     */
    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(source));
    }
}
