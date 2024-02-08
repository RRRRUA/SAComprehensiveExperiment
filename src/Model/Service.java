package Model;

import java.util.List;


public class Service {

    private static final Dao dao=new Dao();
    public void deletePeople(int id)
    {
        dao.deletePeople(id);
    }
    public void addPeople(Contracts contracts)
    {
        dao.addPeople(contracts);
    }
    public List<Contracts> searchPeoplesByTitle(String keyword) {
        return dao.searchPeoplesByTitle(keyword);
    }
    public void updatePeople(Contracts contracts) {
        dao.updatePeople(contracts);
    }
    public List<Contracts> getAllPeoples()
     {
        return dao.getAllPeoples();
     }
}
