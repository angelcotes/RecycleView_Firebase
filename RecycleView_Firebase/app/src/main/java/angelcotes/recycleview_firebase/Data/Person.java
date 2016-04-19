package angelcotes.recycleview_firebase.Data;

/**
 * Created by Angel on 18/04/2016.
 */
public class Person {
    public Person() {
    }

    public String getNamePerson() {
        return namePerson;
    }

    public void setNamePerson(String namePerson) {
        this.namePerson = namePerson;
    }

    public String getIdPerson() {
        return IdPerson;
    }

    public void setIdPerson(String idPerson) {
        IdPerson = idPerson;
    }

    private String namePerson;
    private String IdPerson;
}
