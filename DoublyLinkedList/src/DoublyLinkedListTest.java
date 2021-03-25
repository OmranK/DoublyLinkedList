import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class DoublyLinkedListTest {

    private DoublyLinkedList<Integer> emptyList;
    private DoublyLinkedList<Integer> singleElementList;
    private DoublyLinkedList<Integer> multipleElementList;

    @BeforeEach
    public void setUp()
    {
        // []
        emptyList = new DoublyLinkedList<Integer>();
        assertEquals( "", emptyList.toString() );

        // [8]
        singleElementList = new DoublyLinkedList<Integer>();
        singleElementList.add(8);
        assertEquals( "8", singleElementList.toString() );

        // [4 3 5 7 1 6]
        multipleElementList = new DoublyLinkedList<Integer>();
        multipleElementList.addFirst(6);
        multipleElementList.addFirst(1);
        multipleElementList.addFirst(7);
        multipleElementList.addFirst(5);
        multipleElementList.addFirst(3);
        multipleElementList.addFirst(4);
        assertEquals("4 ==> 3 ==> 5 ==> 7 ==> 1 ==> 6", multipleElementList.toString() );
    }

    @Test
    void testConstructor_createsEmptyList() {
        assertTrue(emptyList instanceof DoublyLinkedList);
    }

    @Test
    void testConstructor_createsListFromCollection() {
        ArrayList<Integer> array = new ArrayList();
        array.add(4);
        array.add(5);
        DoublyLinkedList<Integer> listFromArray = new DoublyLinkedList(array);
        assertEquals("4 ==> 5", listFromArray.toString());
    }

    @Test
    void testClone_createsShallowCopyOfList() {
        Object clone = multipleElementList.clone();
        assertTrue(clone.equals(multipleElementList));
        assertTrue(clone instanceof DoublyLinkedList);
        assertEquals( multipleElementList.toString(), clone.toString());
    }

    @Test
    void testSize_addingElements_returnsCorrectListSize() {
        emptyList.add(5);
        emptyList.addFirst(3);
        assertEquals(2, emptyList.size());
    }

    @Test
    void testSize_removingElements_returnsCorrectListSize() {
        singleElementList.remove();
        assertEquals(0, singleElementList.size());
        multipleElementList.remove();
        assertEquals(5, multipleElementList.size());
    }

    @Test
    void testRemove_removingElements_throwsNoSuchELementException() {
        assertThrows(NoSuchElementException.class , () -> {
            emptyList.remove();
        });
    }

    @Test
    void testGetFirst_returnsTheFirstElement() {
        assertEquals(8, singleElementList.getFirst());
        assertEquals(4, multipleElementList.getFirst());
    }

    @Test
    void testGetLast_returnsTheLastElement() {
        assertEquals(8, singleElementList.getLast());
        assertEquals(6, multipleElementList.getLast());
    }

    @Test
    void testGetFirstAndGetLast_ifListIsEmpty_throwsNoSuchElementException() {
        assertThrows(NoSuchElementException.class , () -> {
            emptyList.getFirst();
        });
        assertThrows(NoSuchElementException.class , () -> {
            emptyList.getLast();
        });
    }

    @Test
    void testAdd_addingAnElement_AddsElementCorrectlyToListTailAndReturnsTrue() {
        emptyList.add(2);
        assertEquals(2, emptyList.getLast());
        boolean returnedValue = multipleElementList.add(3);
        assertEquals(3, multipleElementList.getLast());
        assertTrue(returnedValue);
    }

    @Test
    void testAdd_addingAnElementAtSpecifiedIndex_InsertsElementCorrectlyIntoList() {
        multipleElementList.add(2, 4);
        assertEquals("4 ==> 3 ==> 4 ==> 5 ==> 7 ==> 1 ==> 6", multipleElementList.toString());
    }

    @Test
    void testAddAll_addingCollection_AddsCollectionElementsCorrectlyToListTail() {
        ArrayList<Integer> array = new ArrayList();
        array.add(4);
        array.add(5);
        emptyList.addAll(array);
        assertEquals("4 ==> 5", emptyList.toString());
    }

    @Test
    void testAddAll_addingCollectionAtSpecifiedIndex_InsertsCollectionElementsCorrectlyIntoList() {
        ArrayList<Integer> array = new ArrayList();
        array.add(4);
        array.add(5);
        multipleElementList.addAll(2, array);
        assertEquals("4 ==> 3 ==> 4 ==> 5 ==> 5 ==> 7 ==> 1 ==> 6", multipleElementList.toString());
    }

    @Test
    void testClear_clearingList_CorrectlyClearsList() {
        multipleElementList.clear();
        assertEquals(0, multipleElementList.size());
        assertEquals("", multipleElementList.toString());
    }

    @Test
    void testContains_searchingListForElement_CorrectlyReturnsBooleanForPresenceCheck() {
        assertTrue(multipleElementList.contains(7));
        assertFalse(multipleElementList.contains(8));
    }

    @Test
    void testGet_gettingElementAtSpecifiedIndex_returnsCorrectElementFromSpecifiedIndex() {
        assertEquals(6, multipleElementList.get(5));
        assertEquals(3, multipleElementList.get(1));
    }

    @Test
    void testGet_searchingForIndexOfElement_returnsIndexOfFirstOccuranceOfElement() {
        assertEquals(0, multipleElementList.indexOf(4));
        assertEquals(5, multipleElementList.indexOf(6));
    }

    @Test
    void testGet_searchingForLastIndexOfElement_returnsIndexOfLastOccuranceOfElement() {
        multipleElementList.add(6);
        multipleElementList.add(4);
        assertEquals(7, multipleElementList.lastIndexOf(4));
        assertEquals(6, multipleElementList.lastIndexOf(6));
    }

    @Test
    void testRemove_removingElementAtValidIndex_removesCorrectElementFromListAndReturnsIt() {
        Integer removedElement = multipleElementList.remove(2);
        assertEquals("4 ==> 3 ==> 7 ==> 1 ==> 6", multipleElementList.toString());
        assertEquals(5, removedElement);
    }

    @Test
    void testRemove_removingElementAtInvalidIndex_throwsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class , () -> {
            emptyList.remove(3);
        });
        assertThrows(IndexOutOfBoundsException.class , () -> {
            multipleElementList.remove(8);
        });
    }

    @Test
    void testRemove_removingElementFromList_removesElementIfItExistsAndReturnsBooleanOutcome() {
        boolean returnValue1 = multipleElementList.remove((Integer) 7);
        assertTrue(returnValue1);
        assertEquals("4 ==> 3 ==> 5 ==> 1 ==> 6", multipleElementList.toString());
        boolean returnValue2 = multipleElementList.remove((Integer) 8);
        assertFalse(returnValue2);
        assertEquals("4 ==> 3 ==> 5 ==> 1 ==> 6", multipleElementList.toString());
    }

    @Test
    void testSet_settingElementAtSpecifiedIndex_replacesElementAtSpecifiedIndexCorrectlyAndReturnsOldValue() {
        int returnValue1 = multipleElementList.set(0, 2);
        int returnValue2 = multipleElementList.set(5, 1);
        assertEquals("2 ==> 3 ==> 5 ==> 7 ==> 1 ==> 1", multipleElementList.toString());
        assertEquals(4, returnValue1);
        assertEquals(6, returnValue2);
    }

    @Test
    void testSet_settingElementAtInvalidIndex_throwsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class , () -> {
            emptyList.set(3, 5);
        });
        assertThrows(IndexOutOfBoundsException.class , () -> {
            multipleElementList.set(8, 5);
        });
    }

    @Test
    void testToArray_creatingArrayFromList_returnsCorrectArrayWithAllElements() {
        Object[] arrayFromList = multipleElementList.toArray();
        int[] array = {4, 3, 5, 7, 1, 6};
        assertEquals(6, arrayFromList.length);

        for (int i = 0; i < arrayFromList.length; i++) {
            assertEquals(array[i], arrayFromList[i]);
        }
    }

    @Test
    void testToArray_populatingSpecifiedArrayOfSomeTypeFromList_populatesArrayCorrectlyWithAllElements() {
        int[] array = {4, 3, 5, 7, 1, 6};
        Integer[] arrayFromList = {};
        arrayFromList = multipleElementList.toArray(arrayFromList);

        assertEquals(6, arrayFromList.length);
        for (int i = 0; i < arrayFromList.length; i++) {
            assertEquals(array[i], arrayFromList[i]);
        }
    }

    @Test
    void testAddFirst_addingAnElementToTheFront_addsElementCorrectlyToTheFrontOfTheList() {
        singleElementList.addFirst(5);
        assertEquals(2, singleElementList.size());
        assertEquals("5 ==> 8", singleElementList.toString());
    }

    @Test
    void testAddLast_addingAnElementToTheEnd_addsElementCorrectlyToTheEndOfTheList() {
        singleElementList.addLast(5);
        assertEquals(2, singleElementList.size());
        assertEquals("8 ==> 5", singleElementList.toString());
    }

    @Test
    void testElement_checkingElement_returnsTheFirstElementOfTheList() {
        assertEquals(8, singleElementList.element());
        assertEquals("8", singleElementList.toString());
    }

    @Test
    void testOffer_offeringElement_appendsTheElementToTheEndOfTheList() {
        singleElementList.offer(5);
        assertEquals(2, singleElementList.size());
        assertEquals("8 ==> 5", singleElementList.toString());
    }

    @Test
    void testOfferFirst_offeringFirstElement_appendsTheElementToTheFrontOfTheList() {
        singleElementList.offerFirst(5);
        assertEquals(2, singleElementList.size());
        assertEquals("5 ==> 8", singleElementList.toString());
    }

    @Test
    void testOfferLast_offeringLastElement_appendsTheElementToTheEndOfTheList() {
        singleElementList.offerLast(5);
        assertEquals(2, singleElementList.size());
        assertEquals("8 ==> 5", singleElementList.toString());
    }

    @Test
    void testPeek_peekingElement_returnsTheFirstElementOfTheList() {
        int returnedValue = multipleElementList.peek();
        assertEquals(4, returnedValue);
        assertEquals("4 ==> 3 ==> 5 ==> 7 ==> 1 ==> 6", multipleElementList.toString() );
    }

    @Test
    void testPeekFirst_peekingFirstElement_returnsTheFirstElementOfTheList() {
        int returnedValue = multipleElementList.peekFirst();
        assertEquals(4, returnedValue);
        assertEquals("4 ==> 3 ==> 5 ==> 7 ==> 1 ==> 6", multipleElementList.toString() );
    }

    @Test
    void testPeekLast_peekingLastElement_returnsTheLastElementOfTheList() {
        int returnedValue = multipleElementList.peekLast();
        assertEquals(6, returnedValue);
        assertEquals("4 ==> 3 ==> 5 ==> 7 ==> 1 ==> 6", multipleElementList.toString() );
    }

    @Test
    void testPoll_pollingElement_removesAndReturnsTheFirstElementOfTheList() {
        int returnedValue = multipleElementList.poll();
        assertEquals(4, returnedValue);
        assertEquals("3 ==> 5 ==> 7 ==> 1 ==> 6", multipleElementList.toString() );
    }

    @Test
    void testPollFirst_pollingFirstElement_removesAndReturnsTheFirstElementOfTheList() {
        int returnedValue = multipleElementList.pollFirst();
        assertEquals(4, returnedValue);
        assertEquals("3 ==> 5 ==> 7 ==> 1 ==> 6", multipleElementList.toString() );
    }

    @Test
    void testPollLast_pollingLastElement_removesAndReturnsTheLastElementOfTheList() {
        int returnedValue = multipleElementList.pollLast();
        assertEquals(6, returnedValue);
        assertEquals("4 ==> 3 ==> 5 ==> 7 ==> 1", multipleElementList.toString() );
    }

    @Test
    void testPop_poppingElement_removesAndReturnsTheLastElementOfTheList() {
        int returnedValue = multipleElementList.pop();
        assertEquals(6, returnedValue);
        assertEquals("4 ==> 3 ==> 5 ==> 7 ==> 1", multipleElementList.toString() );
    }

    @Test
    void testPush_pushingElement_appendsElementToTheEndOfTheList() {
        multipleElementList.push(5);
        assertEquals(7, multipleElementList.size());
        assertEquals("4 ==> 3 ==> 5 ==> 7 ==> 1 ==> 6 ==> 5", multipleElementList.toString() );
    }

    @Test
    void testRemove_removingElement_removesAndReturnsTheFirstElementOfTheList() {
        int returnedValue = multipleElementList.remove();
        assertEquals(4, returnedValue);
        assertEquals("3 ==> 5 ==> 7 ==> 1 ==> 6", multipleElementList.toString() );
    }

    @Test
    void testRemoveFirstOccurance_removingFirstOccuranceOfElement_removesTheFirstOccuranceOfElementFromTheListCorrectlyAndReturnsBooleanOutcome() {
        boolean returnedValue = singleElementList.removeFirstOccurrence(8);
        assertTrue(returnedValue);
        assertEquals(0, singleElementList.size());
        assertEquals("", singleElementList.toString() );
        multipleElementList.addLast(7);
        boolean returnedValue2 = multipleElementList.removeFirstOccurrence(7);
        assertTrue(returnedValue2);
        assertEquals(6, multipleElementList.size());
        assertEquals("4 ==> 3 ==> 5 ==> 1 ==> 6 ==> 7", multipleElementList.toString() );
        boolean returnedValue3 = multipleElementList.removeFirstOccurrence(8);
        assertFalse(returnedValue3);
        assertEquals(6, multipleElementList.size());
        assertEquals("4 ==> 3 ==> 5 ==> 1 ==> 6 ==> 7", multipleElementList.toString() );
    }

    @Test
    void testRemoveLast_removingLastElement_removesAndReturnsTheLastElementOfTheList() {
        int returnedValue = multipleElementList.removeLast();
        assertEquals(6, returnedValue);
        assertEquals(5, multipleElementList.size());
        assertEquals("4 ==> 3 ==> 5 ==> 7 ==> 1", multipleElementList.toString() );
    }


    @Test
    void testRemoveLast_removingLastElementFromEmptyList_throwsNoSuchELementException() {
        assertThrows(NoSuchElementException.class , () -> {
            emptyList.removeLast();
        });
    }

    @Test
    void testRemoveLastOccurance_removingLastOccuranceOfElement_removesTheLastOccuranceOfElementFromTheListCorrectlyAndReturnsBooleanOutcome() {
        boolean returnedValue = singleElementList.removeLastOccurrence(8);
        assertTrue(returnedValue);
        assertEquals(0, singleElementList.size());
        assertEquals("", singleElementList.toString() );
        multipleElementList.addLast(7);
        boolean returnedValue2 = multipleElementList.removeLastOccurrence(7);
        assertTrue(returnedValue2);
        assertEquals(6, multipleElementList.size());
        assertEquals("4 ==> 3 ==> 5 ==> 7 ==> 1 ==> 6", multipleElementList.toString() );
        boolean returnedValue3 = multipleElementList.removeLastOccurrence(8);
        assertFalse(returnedValue3);
        assertEquals(6, multipleElementList.size());
        assertEquals("4 ==> 3 ==> 5 ==> 7 ==> 1 ==> 6", multipleElementList.toString() );
    }
}