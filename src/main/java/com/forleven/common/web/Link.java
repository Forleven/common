package com.forleven.common.web;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Wither;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.util.Assert;
import org.springframework.web.util.UriTemplate;

/**
 * Value object for links.
 * @see <a href="https://github.com/spring-projects/spring-hateoas/blob/master/src/main/java/org/springframework/hateoas/Link.java">Reference in Github</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@EqualsAndHashCode(of = { "rel", "href" })
public class Link implements Serializable {

    private static final long serialVersionUID = -9037755944661782121L;

    public static final String REL_SELF = "self";
    public static final String REL_FIRST = "first";
    public static final String REL_PREVIOUS = "prev";
    public static final String REL_NEXT = "next";
    public static final String REL_LAST = "last";

    private @XmlAttribute
    @Wither
    String rel;
    private @XmlAttribute @Wither String href;
    private @XmlTransient
    @JsonIgnore
    UriTemplate template;

    /**
     * Creates a new {@link Link} to the given URI with the given rel.
     *
     * @param href must not be {@literal null} or empty.
     * @param rel must not be {@literal null} or empty.
     */
    public Link(String href, String rel) {
        this(new UriTemplate(href), rel);
    }

    /**
     * Creates a new Link from the given {@link UriTemplate} and rel.
     *
     * @param template must not be {@literal null}.
     * @param rel must not be {@literal null} or empty.
     */
    public Link(UriTemplate template, String rel) {

        Assert.notNull(template, "UriTemplate must not be null!");
        Assert.hasText(rel, "Rel must not be null or empty!");

        this.template = template;
        this.href = template.toString();
        this.rel = rel;
    }
}
