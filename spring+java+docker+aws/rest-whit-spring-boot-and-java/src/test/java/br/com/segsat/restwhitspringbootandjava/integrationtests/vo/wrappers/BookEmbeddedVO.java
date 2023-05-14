package br.com.segsat.restwhitspringbootandjava.integrationtests.vo.wrappers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.xml.bind.annotation.XmlRootElement;
import br.com.segsat.restwhitspringbootandjava.integrationtests.vo.v1.BookVO;

@XmlRootElement
public class BookEmbeddedVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("bookVOList")
    private List<BookVO> books = new ArrayList<>();

    public BookEmbeddedVO() {
    }

    public BookEmbeddedVO(List<BookVO> BookVOList) {
        this.books = BookVOList;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public List<BookVO> getBooks() {
        return books;
    }

    public void setBooks(List<BookVO> BookVOList) {
        this.books = BookVOList;
    }

    public void addBookVOList(BookVO BookVO) {
        if (this.books == null){
            this.books = new ArrayList<>();
        }
        this.books.add(BookVO);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((books == null) ? 0 : books.hashCode());
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
        BookEmbeddedVO other = (BookEmbeddedVO) obj;
        if (books == null) {
            if (other.books != null)
                return false;
        } else if (!books.equals(other.books))
            return false;
        return true;
    }

    

}
