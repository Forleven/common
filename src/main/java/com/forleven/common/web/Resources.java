package com.forleven.common.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Wither;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.collect.ImmutableMap;

import org.springframework.data.domain.Page;
import org.springframework.util.Assert;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


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
    private final List<Link> links = new ArrayList<>();

    @JsonIgnore
    private final Page<T> page;

    /**
     * Resources class inspiration in Spring HAL. Follow Reference in github of spring
     * @see <a href="http://goo.gl/m5MPK2">http://goo.gl/m5MPK2</a>
     * @param page Receive a paged items
     */
    public Resources(Page<T> page) {
        this.page = page;
        this.data = page.getContent();
        this.count = this.data.size();

        add(buildPageLink(1, Link.REL_FIRST));
        add(buildPageLink(page.getTotalPages(), Link.REL_LAST));
        add(buildPageLink(page.getNumber() + 1,  Link.REL_SELF));

        if(page.hasPrevious()) {
            String path = createBuilder()
                    .queryParam("page",page.previousPageable().getPageNumber() + 1)
                    .build()
                    .toUriString();

            add(new Link(path, Link.REL_PREVIOUS));
        }

        if(page.hasNext()) {
            String path = createBuilder()
                    .queryParam("page",page.nextPageable().getPageNumber() + 1)
                    .build()
                    .toUriString();

            add(new Link(path, Link.REL_NEXT));
        }
    }

    private ServletUriComponentsBuilder createBuilder() {
        return ServletUriComponentsBuilder.fromCurrentRequestUri();
    }

    private Link buildPageLink(int page, String rel) {
        String path = createBuilder()
                .queryParam("page", page)
                .build()
                .toUriString();

        return new Link(path,rel);
    }

    private void add(Link link) {
        Assert.notNull(link, "Link must not be null!");
        this.links.add(link);
    }

    public Map<String, Map<String, String>> getLinks() {
        return links.stream()
                .collect(Collectors.toMap(Link::getRel, link -> ImmutableMap.of("href", link.getHref())));
    }

    @Override
    public Iterator<T> iterator() {
        return data.iterator();
    }
}

