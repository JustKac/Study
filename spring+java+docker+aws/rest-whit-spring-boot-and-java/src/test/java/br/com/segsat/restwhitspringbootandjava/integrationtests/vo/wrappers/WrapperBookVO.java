package br.com.segsat.restwhitspringbootandjava.integrationtests.vo.wrappers;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WrapperBookVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private BookEmbeddedVO embedded;

    public WrapperBookVO() {
    }

    public WrapperBookVO(BookEmbeddedVO embedded) {
        this.embedded = embedded;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public BookEmbeddedVO getEmbedded() {
        return embedded;
    }

    public void setEmbedded(BookEmbeddedVO embedded) {
        this.embedded = embedded;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((embedded == null) ? 0 : embedded.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WrapperBookVO other = (WrapperBookVO) obj;
        if (embedded == null) {
            if (other.embedded != null)
                return false;
        } else if (!embedded.equals(other.embedded))
            return false;
        return true;
    }

}
