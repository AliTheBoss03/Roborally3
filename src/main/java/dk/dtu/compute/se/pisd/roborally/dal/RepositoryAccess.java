package dk.dtu.compute.se.pisd.roborally.dal;


public class RepositoryAccess {

    private static Repository repository;

    public static IRepository getRepository() {
        if(repository == null) {
            repository = new Repository(new Connector());
        }
        return repository;
    }

}