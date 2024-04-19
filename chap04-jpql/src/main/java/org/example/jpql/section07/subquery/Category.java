package org.example.jpql.section07.subquery;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.example.jpql.section06.join.Menu;

import java.util.List;

@Entity(name = "Section07Category")
@Table(name = "tbl_category")
public class Category {

    @Id
    private int categoryCode;
    private String categoryName;
    private Integer refCategoryCode;


}