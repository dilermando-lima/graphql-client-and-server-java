package example.graphqlserver.dto;

public class ContactFilter {

    private String containsEmail;
    private String containsPhone;
    private Integer pageSize;
    private Integer pageNum;

    public String getContainsEmail() {
        return containsEmail;
    }

    public void setContainsEmail(String containsEmail) {
        this.containsEmail = containsEmail;
    }

    public String getContainsPhone() {
        return containsPhone;
    }

    public void setContainsPhone(String containsPhone) {
        this.containsPhone = containsPhone;
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

}
