/*
Author: Alejandro Lopez
Class: CSIS 3810 L01
Started Working on Project: 09/24/2021
Stopped Working on Project: 10/18/2021
 */


import java.io.*;



                                                                    // **DID NOT USE BUT MIGHT USE IN THE FUTURE TO IMPLEMENT ROUND ROBIN**
                                                                    // **DID NOT USE BUT MIGHT USE IN THE FUTURE TO IMPLEMENT ROUND ROBIN**
                                                                    // **DID NOT USE BUT MIGHT USE IN THE FUTURE TO IMPLEMENT ROUND ROBIN**
                                                                    // **DID NOT USE BUT MIGHT USE IN THE FUTURE TO IMPLEMENT ROUND ROBIN**
public class QueArr
{
    private int maxSize;
    private int front;
    private int rear;
    private int items[];

    public void QueArr()
    {
        maxSize = 100;
        front = maxSize - 1;
        rear = maxSize - 1;
        items = new int[maxSize];
    }


    public void QueArr( int size )
    {
        maxSize = size;
        front = maxSize - 1;
        rear = maxSize - 1;
        items = new int[maxSize];
    }


    public void Destructor()
    {
        items = null;
        System.gc();
    }


    public void makeEmpty()
    {
        front = maxSize - 1;
        rear = maxSize - 1;
    }


    public boolean isEmpty()
    {
        return (rear == front);
    }


    public boolean isFull()
    {
        return ((rear+1)%maxSize == front);

    }


    public void enqueue(int item)
    {
        if (isFull())
            System.out.println("Queue is full and item cannot be added.\n");
        else
        {
            rear = (rear + 1) % maxSize;
            items[rear] = item;
        }
    }


    public void dequeue(int item)
    {
        if (isEmpty())
            System.out.println("Queue is Empty and there is no item to remove.\n");
        else
        {
            front = (front + 1) % maxSize;
            item = items[front];
        }
    }


    public void printQueue()
    {
        if (isEmpty())
            System.out.println("Queue is Empty.\n");
        else
        {
            int temp = front;
            while (temp != rear)
            {
                temp = (temp + 1) % maxSize;
                System.out.println(items[temp] + " ");
            }
            System.out.println();
        }

    }
};







