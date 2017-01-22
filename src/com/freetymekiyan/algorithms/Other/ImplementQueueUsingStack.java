package com.freetymekiyan.algorithms.Other;

import java.util.LinkedList;
import java.util.Stack;

public class ImplementQueueUsingStack {
    private java.util.Stack<Integer> stack = new java.util.Stack<Integer>();

    public void insert(Integer elem) {
        if (!stack.empty()) {
            Integer topElem = stack.pop();
            insert(elem);
            stack.push(topElem);
        } else
            stack.push(elem);
    }

    public Integer remove() {
        return stack.pop();
    }
}

class MyQueue<T> {

    private Stack<T> inputStack;      // for enqueue
    private Stack<T> outputStack;     // for dequeue
    private int size;

    public MyQueue() {
        inputStack = new Stack<>();
        outputStack = new Stack<>();
    }

    public void enqueue(T e) {
        inputStack.push(e);
        size++;
    }

    public T dequeue() {
        // fill out all the Input if output stack is empty
        if(outputStack.isEmpty())
            while(!inputStack.isEmpty())
                outputStack.push(inputStack.pop());

        T temp = null;
        if(!outputStack.isEmpty()) {
            temp = outputStack.pop();
            size--;
        }

        return temp;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

}

class QueueUsingStacks<T>
{
    private Stack<T> stack1;
    private Stack<T> stack2;

    public QueueUsingStacks()
    {
        stack1 = new Stack<T>();
        stack2 = new Stack<T>();

    }
    public void copy(Stack<T> source, Stack<T> dest )
    {
        while (!source.isEmpty()) {
            dest.push(source.pop());
        }
    }
    public void enqueue(T entry)
    {

        stack1.push(entry);
    }
    public T dequeue() throws Exception {
        T obj;
        if (stack2 != null)
        {
            copy(stack1, stack2);
            obj = stack2.pop();
            copy(stack2, stack1);
        }
        else
        {
            throw new Exception("Stack is empty");
        }
        return obj;
    }

}