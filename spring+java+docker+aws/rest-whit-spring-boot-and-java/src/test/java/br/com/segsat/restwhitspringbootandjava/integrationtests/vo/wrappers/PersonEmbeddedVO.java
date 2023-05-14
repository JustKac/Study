package br.com.segsat.restwhitspringbootandjava.integrationtests.vo.wrappers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.xml.bind.annotation.XmlRootElement;
import br.com.segsat.restwhitspringbootandjava.integrationtests.vo.v1.PersonVO;

@XmlRootElement
public class PersonEmbeddedVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("personVOList")
    private List<PersonVO> persons = new ArrayList<>();

    public PersonEmbeddedVO() {
    }

    public PersonEmbeddedVO(List<PersonVO> personVOList) {
        this.persons = personVOList;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public List<PersonVO> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonVO> personVOList) {
        this.persons = personVOList;
    }

    public void addPersonVOList(PersonVO personVO) {
        if (this.persons == null){
            this.persons = new ArrayList<>();
        }
        this.persons.add(personVO);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((persons == null) ? 0 : persons.hashCode());
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
        PersonEmbeddedVO other = (PersonEmbeddedVO) obj;
        if (persons == null) {
            if (other.persons != null)
                return false;
        } else if (!persons.equals(other.persons))
            return false;
        return true;
    }

    

}
