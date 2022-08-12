package com.ukg.framework.common.tasks;

public class Task3 {



    // Please do not change this interface

    interface ListNode {
        int getItem();
        ListNode getNext();
        void setNext(ListNode next);
    }

    public static ListNode reverse(ListNode node) {
        /*
          Please implement this method to
          reverse a given linked list.
        */

        ListNode temp = null, prev = null;
        while(node != null){
            temp = node.getNext();
            node.setNext(prev);
            prev = node;
            node = temp;

        }
        return prev;
    }

}
