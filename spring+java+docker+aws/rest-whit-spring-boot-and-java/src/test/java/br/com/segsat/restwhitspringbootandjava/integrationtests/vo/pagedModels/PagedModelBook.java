package br.com.segsat.restwhitspringbootandjava.integrationtests.vo.pagedModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.segsat.restwhitspringbootandjava.integrationtests.vo.v1.BookVO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PagedModelBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "content")
    private List<BookVO> content;

    public PagedModelBook() {
    }

    public PagedModelBook(List<BookVO> content) {
        this.content = content;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public List<BookVO> getContent() {
        return content;
    }

    public void setContent(List<BookVO> content) {
        this.content = content;
    }

    public void addContent(BookVO book) {
        if (this.content == null){
            this.content = new ArrayList<>();
        }
        this.content.add(book);;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((content == null) ? 0 : content.hashCode());
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
        PagedModelBook other = (PagedModelBook) obj;
        if (content == null) {
            if (other.content != null)
                return false;
        } else if (!content.equals(other.content))
            return false;
        return true;
    }

}
