package demo.entity;

public class ContactBuilder {
    protected Integer id;
    protected String name;
    protected String phone;
    public ContactBuilder withID(Integer id){
        this.id = id;
        return this;
    }

    public ContactBuilder withName(String name){
        this.name = name;
        return this;
    }

    public ContactBuilder withPhone (String phone){
        this.phone = phone;
        return this;
    }

    public Contact build(){
        return new Contact(this);
    }
}
