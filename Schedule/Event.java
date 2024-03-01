import java.time.LocalDate;

public class Event{
    private String name;
    private LocalDate dueDate;
    private String description;

    public Event(String name, LocalDate dueDate, String description){
        this.name = name;
        this.dueDate = dueDate;
        this.description = description;
    }

    public String getName(){
        return name;
    }

    public LocalDate getDueDate(){
        return dueDate;
    }

    public String getDescription(){
        return description;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDueDate(LocalDate dueDate){
        this.dueDate = dueDate;
    }

    public void setDescription(String description){
        this.description = description;
    }


}