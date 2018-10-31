package com.forleven.common.web;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Wither;

import io.vavr.control.Try;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static com.forleven.common.hateoas.mvc.ControllerLinkBuilder.linkTo;


@Data
@Wither
@AllArgsConstructor
@JsonRootName("data")
public class Resources<T> implements Iterable<T> {

    @JsonProperty("count")
    private final int count;

    @JsonProperty("data")
    private final List<T> data;

    @JsonProperty("links")
    private final Links links;

    @JsonIgnore
    private final Page<T> page;

    static <T> Resources<T> empty() {
        return new Resources<>(Page.empty());
    }

    /**
     * Resources class inspiration in Spring HAL. Follow Reference in github of spring
     * @see <a href="http://goo.gl/m5MPK2">http://goo.gl/m5MPK2</a>
     * @param page Receive a paged items
     */
    public Resources(Page<T> page) {
        this.page = page;
        this.data = page.getContent();
        this.count = this.data.size();
        this.links = new Links();

        buildPageLink(1).forEach(links::setFirst);

        buildPageLink(page.getTotalPages()).forEach(links::setLast);

        buildPageLink(page.getNumber() + 1).forEach(links::setSelf);

        if(page.hasPrevious()) {
            buildPageLink(page.previousPageable().getPageNumber() + 1).forEach(links::setPrevious);
        }

        if(page.hasNext()) {
            buildPageLink(page.nextPageable().getPageNumber() + 1).forEach(links::setNext);
        }
    }

    private ServletUriComponentsBuilder createBuilder() {
        return ServletUriComponentsBuilder.fromCurrentRequestUri();
    }

    private Try<Link> buildPageLink(int page) {
        return Try.of(() -> {
            String path = createBuilder()
                    .queryParam("page", page)
                    .build()
                    .toUriString();

            return new Link(path, page);
        });
    }

    @Override
    public Iterator<T> iterator() {
        return data.iterator();
    }

    @JsonIgnore
    public Stream<T> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    @JsonIgnore
    public int getSize() {
        return this.page.getSize();
    }
}

