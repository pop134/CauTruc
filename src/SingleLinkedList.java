
public class SingleLinkedList {
    public Node first;
    public Node last;

    public SingleLinkedList() {
        first = last = null;
    }

    public void insert(int data) {
        Node temp = new Node(data);
        if (first == null) {
            temp.next = null;
            first = temp;
            last = temp;
        } else {
            temp.next = null;
            last.next = temp;
            last = temp;
        }
    }

    public Node delete(int data) {
        Node tmp = first;
        Node prev = first;

        while (tmp != null) {
            if (tmp.data == data) {
                if (tmp == first) {
                    if (tmp == last) {
                        first = last = null;
                    } else {
                        first = tmp.next;
                    }
                } else if (tmp == last) {
                    prev.next = tmp.next;
                    last = prev;
                } else {
                    prev.next = tmp.next;
                }
                return tmp;
            }

            prev = tmp;
            tmp = tmp.next;
        }
        return null;
    }

    public void printList() {
        Node temp = first;
        if (first == null) {
            System.out.println("List is Empty");
        } else {
            while (temp != null) {
                temp.printNode();
                temp = temp.next;
            }
        }
    }

    public Boolean isEmpty() {
        return first == null;
    }
}
 