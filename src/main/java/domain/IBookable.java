package domain; 
public interface IBookable { 
    boolean checkAvailability(String date);
     void book(String date);
}