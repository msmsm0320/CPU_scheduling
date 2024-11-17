import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class PCB{
    int arrivaltime;    //A
    int cpubursttime;   //B
    int totalcputime;   //C
    int iobursttime;    //IO

    public PCB(int arrivaltime, int totalcputime, int cpubursttime, int iobursttime) {
        this.arrivaltime = arrivaltime;
        this.cpubursttime=cpubursttime;
        this.totalcputime=totalcputime;
        this.iobursttime = iobursttime;
    }
}

class FCFS{
    private int finalfinishingtime=0; // finalfinishingtime = 모든 프로세스 finishingtime의 합
    private int finaltotaliotime; // 총 iotime의 합
    private int finaltotalcputime=0;
    private int finaltotalturnaroundtime=0;
    private int finaltotalwaitingtime=0;
    
    public void Run_FCFS(List<PCB> processes){
        Collections.sort(processes, Comparator.comparingInt(p -> p.arrivaltime));
        int currenttime=0;
        int finishingtime=0;// finishingtime = totalcputime + iobursttime
        int turnaroundtime=0; // turnaroundtime = finishingtime - arrivaltime
        int remaining_cpu_time = 0;
        int totaliotime=0;
        int waitingtime=0;
        double Throughput=0;
        int k=0;
        Random random = new Random();
        int rand_cpu=0;
        int rand_io =0;

        while(k<processes.size()){
            PCB process = processes.get(k);
            currenttime = process.arrivaltime;  //currenttime은  Process의 arrival time    
            remaining_cpu_time = process.totalcputime;  //remaining_cpu_time은 process의 totalcputime

            while(remaining_cpu_time !=0){
                try {
                    rand_cpu=random.nextInt(process.cpubursttime)+1; // cpubursttime이 scheduling 한 번 진행 될 때마다 random값으로 정해진다. 
                 } catch (IllegalArgumentException e) {
                     rand_cpu=0;
                 }

                 try {
                    rand_io=random.nextInt(process.iobursttime)+1; // iobursttime이 scheduling 한 번 진행 될 때마다 random값으로 정해진다. 
                 } catch (IllegalArgumentException e) {
                     rand_io=0;
                 }

                 
                if(remaining_cpu_time <= process.cpubursttime) {
                    finishingtime += remaining_cpu_time;
                    remaining_cpu_time -=rand_cpu;
                    break;
                }else
                {
                    remaining_cpu_time -= rand_cpu;
                    finishingtime += rand_cpu;
                    finishingtime += rand_io;
                    totaliotime += rand_io;
                }
            }

            turnaroundtime = finishingtime - currenttime;
            // waitingtime은 process가 처음으로 실행 되는 시간 단, 처음의 경우 currentime이다.
            if(process == processes.get(0) && currenttime  == 0){
                waitingtime = currenttime;
            }
            else if (process == processes.get(0)){
                waitingtime = this.finalfinishingtime;
            }
            else{
            waitingtime = this.finalfinishingtime - currenttime;
            }
            this.finalfinishingtime = +finishingtime;
            this.finaltotalcputime =+process.totalcputime;
            

            System.out.println("PCB : ("+process.arrivaltime+","+process.totalcputime+","+process.cpubursttime+","+process.iobursttime+")");
            System.out.println("Finishing time : " + finishingtime);
            System.out.println("Turnaround Time : "+turnaroundtime);
            System.out.println("CPU time : " + process.totalcputime);
            System.out.println("I/O time : "+totaliotime);
            System.out.println("Waiting time : " + waitingtime);
            System.out.println("\n");

            this.finaltotaliotime += totaliotime;
            this.finaltotalcputime += process.totalcputime;
            this.finaltotalturnaroundtime += turnaroundtime;
            this.finaltotalwaitingtime += waitingtime;
            totaliotime = 0;
            k++;  //k는 Scheduling 완료된 프로세스 갯수
            if(k<5){// 다음 process의 arrivaltime이 finishingtime보다 큰 경우 Scheduling이 멈춘다.
                if(processes.get(k).arrivaltime>finishingtime) {
                    System.out.println("더 이상 Scheduling을 진행할 수 없습니다.\n");
                    break;}
                }
        }

        Throughput = (k / (double) this.finalfinishingtime) * 100; // Throughput 계산

        System.out.println("Scheduling 완료된 process의 갯수 : " + k);
        System.out.println("Total Fininshing time : " + this.finalfinishingtime);
        System.out.println("CPU utilization : "+ (double)(this.finalfinishingtime/this.finaltotalcputime)*100);
        System.out.println("I/O utilization : " + (double)(this.finalfinishingtime/this.finaltotaliotime)*100);
        System.out.println("Throughput : " + Throughput);
        System.out.println("Average turnaround time : "+(double)this.finaltotalturnaroundtime/(k));
        System.out.println("Average waiting time : "+(double)this.finaltotalwaitingtime/(k));

    }
}

class SJF{
    private int finalfinishingtime=0; // finalfinishingtime = 모든 프로세스 finishingtime의 합
    private int finaltotaliotime; // 총 iotime의 합
    private int finaltotalcputime=0;
    private int finaltotalturnaroundtime=0;
    private int finaltotalwaitingtime=0;

    public void Run_SJF(List<PCB> processes){
        processes.sort(Comparator.comparingInt(p -> p.cpubursttime));
        List<PCB> cp = new LinkedList<>(processes);
        int currenttime=0;
        int finishingtime=0;// finishingtime = totalcputime + iobursttime
        int turnaroundtime=0; // turnaroundtime = finishingtime - arrivaltime
        int remaining_cpu_time = 0;
        int totaliotime=0;
        int waitingtime=0;
        double Throughput=0;
        int k=0;
        Random random = new Random();
        int rand_cpu=0;
        int rand_io=0;

    
        while(k<processes.size()){
            int findIndex = -1;
            for (int i = 0; i < cp.size(); i++) {
                if (cp.get(i).arrivaltime <= this.finalfinishingtime) {
                    findIndex = i;
                    break;
                }
            }

            if (findIndex != -1) {
                PCB process = cp.get(findIndex);
                cp.remove(findIndex);
                currenttime = process.arrivaltime;  //currenttime은  Process의 arrival time    
                remaining_cpu_time = process.totalcputime;  //remaining_cpu_time은 process의 totalcputime
    
                while(remaining_cpu_time !=0){ 
                    try {
                    rand_cpu=random.nextInt(process.cpubursttime)+1; // cpubursttime이 scheduling 한 번 진행 될 때마다 random값으로 정해진다. 
                 } catch (IllegalArgumentException e) {
                     rand_cpu=0;
                 }

                 try {
                    rand_io=random.nextInt(process.iobursttime)+1; // iobursttime이 scheduling 한 번 진행 될 때마다 random값으로 정해진다. 
                 } catch (IllegalArgumentException e) {
                     rand_io=0;
                 }
                    if(remaining_cpu_time <= process.cpubursttime) {
                        finishingtime += remaining_cpu_time + rand_io;
                        remaining_cpu_time -=rand_cpu;
                        break;
                    }
    
                    remaining_cpu_time -= rand_cpu;
                    finishingtime += rand_cpu;
                    finishingtime += rand_io;
                    totaliotime += rand_io;
                }
    
                turnaroundtime = finishingtime - process.arrivaltime;
                // waitingtime은 process가 처음으로 실행 되는 시간 단, 처음의 경우 currentime이다.
                if(process == processes.get(0) && currenttime  == 0){
                    waitingtime = currenttime;
                }
                else if (process == processes.get(0)){
                    waitingtime = this.finalfinishingtime;
                }
                else{
                waitingtime = this.finalfinishingtime - currenttime;
                }
                this.finalfinishingtime = +finishingtime;
                this.finaltotalcputime =+process.totalcputime;
                
    
                System.out.println("PCB : ("+process.arrivaltime+","+process.totalcputime+","+process.cpubursttime+","+process.iobursttime+")");
                System.out.println("Finishing time : " + finishingtime);
                System.out.println("Turnaround Time : "+turnaroundtime);
                System.out.println("CPU time : " + process.totalcputime);
                System.out.println("I/O time : "+totaliotime);
                System.out.println("Waiting time : " + waitingtime);
                System.out.println("\n");
    
                this.finaltotaliotime += totaliotime;
                this.finaltotalcputime += process.totalcputime;
                this.finaltotalturnaroundtime += turnaroundtime;
                this.finaltotalwaitingtime += waitingtime;
                totaliotime = 0;
                k++;  //k는 Scheduling 완료된 프로세스 갯수
            }
            else{
                System.out.println("더 이상 Scheduling을 진행 할 수 없습니다.\n");
                break;
            }
            } 
            
            Throughput = (k / (double) this.finalfinishingtime) * 100; // Throughput 계산
    
            System.out.println("Scheduling 완료된 process의 갯수 : " + k);
            System.out.println("Total Fininshing time : " + this.finalfinishingtime);
            System.out.println("CPU utilization : "+ (double)(this.finalfinishingtime/this.finaltotalcputime)*100);
            System.out.println("I/O utilization : " + (double)(this.finalfinishingtime/this.finaltotaliotime)*100);
            System.out.println("Throughput : " + Throughput);
            System.out.println("Average turnaround time : "+(double)this.finaltotalturnaroundtime/(k));
            System.out.println("Average waiting time : "+(double)this.finaltotalwaitingtime/(k));


    }
}
class RR{

}


public class Run {

	public static void main(String[] args) throws FileNotFoundException {
        List<PCB> processes = new ArrayList<>();

        Scanner filescan = new Scanner(new File("C:\\Users\\Home\\Desktop\\전민서\\2023년 3학년 1학기\\운영체제\\testcase.txt"));
        int processCount = filescan.nextInt(); // 프로세스 개수를 읽음
        
        for (int i = 0; i < processCount; i++) {
            int arrivalTime = filescan.nextInt();
            int totalCpuTime = filescan.nextInt();
            int cpuburstTime = filescan.nextInt();
            int ioburstTime = filescan.nextInt();
        
            PCB process = new PCB(arrivalTime, totalCpuTime, cpuburstTime, ioburstTime);
            processes.add(process);
        }

        for(PCB p : processes){
            System.out.println(p.arrivaltime +","+p.totalcputime+","+ p.cpubursttime +","+ p.iobursttime);
        }
        System.out.println("================= FCFS scheduling =================");

        FCFS fcfs = new FCFS();
        fcfs.Run_FCFS(processes);

        /*System.out.println("\n================= SJF scheduling =================");
        
        SJF sjf = new SJF();
        sjf.Run_SJF(processes);*/
	}

}
