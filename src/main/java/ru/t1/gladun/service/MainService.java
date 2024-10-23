package ru.t1.gladun.service;


import org.springframework.stereotype.Service;

@Service
public class MainService {
    @LogExecution
    public void startWork() {
        System.out.println("The working day has started. Don't be lazy! ");
    }

    @LogExecution
    @GettingResult
    public String hardlyWork(int number) {
        if(number == 0) {
            return "Lazy bad job! This worker is almost asleep...";
        } else if (number < 0) {
            return "This worker has been sleeping for 3 hours";
        } else {
            return "This worker is a good worker!";
        }
    }

    @LogExecution
    @LogException
    public void accidentallyRemoveSomething() {
        int number = (int) (Math.random() * 4);
        if(number == 0) {
            System.out.println("This worker removed all DataBases");
            throw new RuntimeException("This worker removed all DataBases");
        } else if(number == 1) {
            System.out.println("This worker removed all Microservices");
            throw new RuntimeException("This worker removed all Microservices");
        } else if(number == 2) {
            System.out.println("This worker has worked and hasn't removed anything.");
        } else {
            System.out.println("This employee hasn't done anything today at work, " +
                    "so this worker hasn't removed anything.");
        }
    }

    @LogTracking
    public void goHome() {
        System.out.println("It's 6pm");
        try{
         Thread.sleep(5000);
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("No one is there anymore. \nEveryone is already at home.");
    }
}


