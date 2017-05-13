package com.myapp.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Continent.
 * REF CURSOR PARAM HAS TO BE FIRST
 */
@Entity
@Table(name = "continent")
//@NamedStoredProcedureQueries({
//@NamedStoredProcedureQuery(name = "getContinents", procedureName = "show_continent_noargs",
//parameters = {
		//@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "ref", type = void.class)
		//,@StoredProcedureParameter(mode = ParameterMode.IN, name = "inContinentName", type = String.class)
			//}
//)
//})
public class Continent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "continent_name")
    private String continentName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContinentName() {
        return continentName;
    }

    public Continent continentName(String continentName) {
        this.continentName = continentName;
        return this;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Continent continent = (Continent) o;
        if (continent.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, continent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Continent{" +
            "id=" + id +
            ", continentName='" + continentName + "'" +
            '}';
    }
}