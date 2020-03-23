package com.editor;

public class DoublyLinkedList<T> {
    private Node head; // head of list
    private int size;

    /* Doubly Linked list Node*/
    class Node {
        T data;
        Node prev;
        Node next;

        // Constructor to create a new node
        // next and prev is by default initialized as null
        public Node(T data) {
            this.data = data;
        }
    }

    public Node getHead() {
        return head;
    }

    public DoublyLinkedList() {
        size = 0;
    }

    public int size() { return size; }

    // Adding a node at the front of the list
    public void push(T data) {
        /* 1. allocate node
         * 2. put in the data */
        Node newNode = new Node(data);

        /* 3. Make next of new node as head and previous as NULL */
        newNode.next = head;
        newNode.prev = null;

        /* 4. change prev of head node to new node */
        if (head != null)
            head.prev = newNode;

        /* 5. move the head to point to the new node */
        head = newNode;
        size++;
    }

    // Insert before does not work if nextNode == head;
    public void insertBefore(Node nextNode, T data) {
        Node newNode = new Node(data);
        newNode.prev = nextNode.prev;
        nextNode.prev = newNode;
        newNode.next = nextNode;

        if (newNode.prev != null) {
            newNode.prev.next = newNode;
        }
        size++;
    }

    /* Given a node as prev_node, insert a new node after the given node */
    public void insertAfter(Node prevNode, T data) {
        /*1. check if the given prev_node is NULL */
        if (prevNode == null) {
            System.out.println("The given previous node cannot be NULL.");
            return;
        }

        /* 2. allocate node
         * 3. put in the data */
        Node newNode = new Node(data);

        /* 4. Make next of new node as next of prev_node */
        newNode.next = prevNode.next;

        /* 5. Make the next of prev_node as new_node */
        prevNode.next = newNode;

        /* 6. Make prev_node as previous of new_node */
        newNode.prev = prevNode;

        /* 7. Change previous of new_node's next node */
        if (newNode.next != null)
            newNode.next.prev = newNode;

        size++;
    }

    // Add a node at the end of the list
    void append(T data) {
        /* 1. allocate node
         * 2. put in the data */
        Node newNode = new Node(data);

        Node last = head; /* used in step 5*/

        /* 3. This new node is going to be the last node, so
         * make next of it as NULL*/
        newNode.next = null;

        /* 4. If the Linked List is empty, then make the new
         * node as head */
        if (head == null) {
            newNode.prev = null;
            head = newNode;
            size++;
            return;
        }

        /* 5. Else traverse till the last node */
        while (last.next != null)
            last = last.next;

        /* 6. Change the next of last node */
        last.next = newNode;

        /* 7. Make last node as previous of new node */
        newNode.prev = last;

        size++;
    }

    public Node getNthNode(int nodePos) {
        Node found = head;

        if (size == 0) {
            System.out.println("The list is empty");
            return null;
        }

        if (nodePos == 1) return found;

        for (int i = 2; i <= size; i++) {
            found = found.next;
            if (i == nodePos) break;
        }

        return found;
    }

    // TODO: If the methods works, remember to remove head_ref with this.head
    public void remove(Node node) {
        // Base case
        if (head == null || node == null) {
            return;
        }

        // If node to be deleted is head node
        if (node == head) {
            head = node.next;
        }

        // Change next only if node to be deleted
        // is NOT the last node
        if (node.next != null) {
            node.next.prev = node.prev;
        }

        // Change prev only if node to be deleted
        // is NOT the first node
        if (node.prev != null) {
            node.prev.next = node.next;
        }

        size--;
    }
}
