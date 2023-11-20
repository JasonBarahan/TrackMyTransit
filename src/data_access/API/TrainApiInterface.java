package data_access.API;

public interface TrainApiInterface {
    // This is an interface defining all the shared attributes and methods that all Api classes should have
    //TODO: We might not need an Api interface since the 2 API we are using have little to no relationships with each other
    String API_KEY = System.getenv("API_KEY");

}
