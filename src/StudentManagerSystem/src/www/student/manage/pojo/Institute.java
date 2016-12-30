package www.student.manage.pojo;

public class Institute {
    
    private Integer i_id;
    
    private String i_name;
    
    private String i_manager;

    public Integer getI_id() {
        return i_id;
    }

    public void setI_id(Integer i_id) {
        this.i_id = i_id;
    }

    public String getI_name() {
        return i_name;
    }

    public void setI_name(String i_name) {
        this.i_name = i_name;
    }

    public String getI_manager() {
        return i_manager;
    }

    public void setI_manager(String i_manager) {
        this.i_manager = i_manager;
    }

    @Override
    public String toString() {
        return "Institute [i_id=" + i_id + ", i_name=" + i_name + ", i_manager=" + i_manager + "]";
    }
    
}
