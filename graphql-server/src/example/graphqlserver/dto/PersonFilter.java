package example.graphqlserver.dto;

import java.time.LocalDate;

public class PersonFilter {

    private String containsName;
    private String equalsName;
    private Integer pageSize;
    private Integer pageNum;
    private LocalDate moreThanDateBirth;
    private LocalDate lessThanDateBirth;


    @Override
    public String toString() {
        return "PersonFilter [containsName=" + containsName + ", equalsName=" + equalsName + ", lessThanDateBirth="
                + lessThanDateBirth + ", moreThanDateBirth=" + moreThanDateBirth + ", pageNum=" + pageNum
                + ", pageSize=" + pageSize + "]";
    }


    public String getContainsName() {
        return containsName;
    }
    public void setContainsName(String containsName) {
        this.containsName = containsName;
    }
    public String getEqualsName() {
        return equalsName;
    }
    public void setEqualsName(String equalsName) {
        this.equalsName = equalsName;
    }
    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    public Integer getPageNum() {
        return pageNum;
    }
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
    public LocalDate getMoreThanDateBirth() {
        return moreThanDateBirth;
    }
    public void setMoreThanDateBirth(LocalDate moreThanDateBirth) {
        this.moreThanDateBirth = moreThanDateBirth;
    }
    public LocalDate getLessThanDateBirth() {
        return lessThanDateBirth;
    }
    public void setLessThanDateBirth(LocalDate lessThanDateBirth) {
        this.lessThanDateBirth = lessThanDateBirth;
    }

    
    
}
