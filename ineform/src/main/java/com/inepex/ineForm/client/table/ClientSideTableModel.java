package com.inepex.ineForm.client.table;

@Deprecated
public class ClientSideTableModel /* extends IneTableModel */{

    // /**
    // * This variable has to be decreased with 1 after a new object is created
    // */
    // private long nextNewObjectId = -1L;
    //
    // private final TreeMap<Long, KeyValueObject> createdObjectsCache = new
    // TreeMap<Long, KeyValueObject>();
    // private final List<KeyValueObject> editedObjectsCache = new
    // ArrayList<KeyValueObject>();
    // private final List<KeyValueObject> deletedObjectsCache = new
    // ArrayList<KeyValueObject>();
    // private final List<KeyValueObject> undeletedObjectsCache = new
    // ArrayList<KeyValueObject>();
    //
    // private final ArrayList<KeyValueObject> allObjects = new
    // ArrayList<KeyValueObject>();
    // private ArrayList<KeyValueObject> filteredObjects = new
    // ArrayList<KeyValueObject>();
    // private final List<KeyValueObject> currentDeletedObjects = new
    // ArrayList<KeyValueObject>();
    // private final List<KeyValueObject> currentObjects = new
    // ArrayList<KeyValueObject>();
    //
    // public ClientSideTableModel(EventBus eventBus, String descriptorName) {
    // super(eventBus, descriptorName);
    // }
    //
    // /**
    // * Make sure you will not delete any of this items...
    // *
    // * @return
    // */
    // public List<KeyValueObject> getCurrentObjects() {
    // return currentObjects;
    // }
    //
    // /**
    // * Sets the initial rows of the TableModel
    // *
    // * @param data
    // * the rows as {@link KeyValueObject} collection
    // */
    // public void setInitialData(Collection<KeyValueObject> data) {
    // clearAll(false);
    //
    // if (suppotsIsDeleted) {
    // for (KeyValueObject keyValueObject : data) {
    // if (keyValueObject == null) {
    // continue;
    // }
    // if (keyValueObject.isDeleted()) {
    // currentDeletedObjects.add(keyValueObject);
    // } else {
    // currentObjects.add(keyValueObject);
    // }
    // }
    // } else {
    // currentObjects.addAll(data);
    // }
    //
    // // setRowCount(data.size());
    // fireListChangedEvent();
    // }
    //
    //
    // /**
    // * Clear everything related to this
    // */
    // public void clearAll(boolean fireUpdate) {
    // createdObjectsCache.clear();
    // editedObjectsCache.clear();
    // deletedObjectsCache.clear();
    // undeletedObjectsCache.clear();
    //
    // currentObjects.clear();
    // currentDeletedObjects.clear();
    // allObjects.clear();
    // filteredObjects.clear();
    //
    // if (fireUpdate) {
    // // setRowCount(0);
    // fireListChangedEvent();
    // }
    //
    // }
    //
    // @Override
    // protected void objectCreateRequested(KeyValueObject object) {
    // objectCreateSucceeded(object);
    // }
    //
    // protected void objectCreateSucceeded(KeyValueObject object){
    // try {
    // object.addLong(IFConsts.KEY_ID, nextNewObjectId);
    // currentObjects.add(object);
    // createdObjectsCache.put(nextNewObjectId--, object);
    // setRowCountFireListChange();
    // eventBus.fireEvent(new KeyValueObjectManipulateResultEvent(new
    // ObjectManipulationResult()));
    // } catch (InvalidKeyException e) {
    // System.out.println("onObjectCreated: Could not add id");
    // }
    // }
    //
    // @Override
    // protected void objectEditRequested(KeyValueObject object) {
    // objectEditSucceded(object);
    // }
    //
    // protected void objectEditSucceded(KeyValueObject editedObject){
    //
    // // find the object in objectList
    // long objectId = editedObject.getId();
    // int index = removeObjectFromCaches_GetIndexInCurrObjects(objectId);
    //
    // // only add to edited list if not newly created
    // if (objectId > 0) {
    // editedObjectsCache.add(editedObject);
    // } else {
    // createdObjectsCache.put(objectId, editedObject); // this replaces
    // // the object
    // }
    //
    // // replace object
    // currentObjects.set(index, editedObject);
    //
    // setRowCountFireListChange();
    // eventBus.fireEvent(new KeyValueObjectManipulateResultEvent(new
    // ObjectManipulationResult()));
    // }
    //
    //
    // /**
    // * Finds object in currentObjects, and removes that object form
    // createdObjectCahce and editedObjectCache
    // *
    // * @param id
    // * @return the index of the object in the list 'currentObjects'
    // */
    // private int removeObjectFromCaches_GetIndexInCurrObjects(long id) {
    // try {
    // KeyValueObject foundObj = null;
    // for (KeyValueObject object : currentObjects) {
    // if (object.getId() == id) {
    // foundObj = object;
    // }
    // }
    //
    // if (foundObj != null) {
    // createdObjectsCache.remove(foundObj.getId());
    // editedObjectsCache.remove(foundObj);
    // return currentObjects.indexOf(foundObj);
    // }
    // } catch (Exception ex) {
    // // This should normally not happen, we don't need to do any specific
    // // tasks now
    // }
    // System.out.println("ClientSideTableModel::findObject() failed");
    //
    // return -1;
    // }
    //
    // @Override
    // protected void objectDeleted(KeyValueObject object) {
    // if (object == null) {
    // return;
    // }
    //
    // try {
    // // is it a newly created object? (we don't have to examine if
    // // editedObjectCache contains it)
    // if (createdObjectsCache.containsKey(object.getId())) {
    // createdObjectsCache.remove(object.getId());
    // currentObjects.remove(object);
    // return;
    // }
    // // Is this a simple list that does not support undeleting of
    // // objects?
    // // (and of course the object was not newly created)
    // if (!suppotsIsDeleted) {
    // deletedObjectsCache.add(object);
    // currentObjects.remove(object);
    // return;
    // }
    // // is it an object that was just now undelteted?
    // if (undeletedObjectsCache.contains(object)) {
    // undeletedObjectsCache.remove(object);
    // } else {
    // deletedObjectsCache.add(object);
    // }
    //
    // // switch the current place to currentDeletedObjects list!
    // currentObjects.remove(object);
    // currentDeletedObjects.add(object);
    // object.setDeleleted(true);
    //
    // } finally {
    // setRowCountFireListChange();
    // eventBus.fireEvent(new KeyValueObjectManipulateResultEvent(new
    // ObjectManipulationResult()));
    // }
    // }
    //
    // @Override
    // protected void objectUnDeleted(KeyValueObject object) {
    // if (object == null) {
    // return;
    // }
    //
    // // Is it an object that was just now deleted?
    // if (deletedObjectsCache.contains(object)) {
    // deletedObjectsCache.remove(object);
    // } else {
    // undeletedObjectsCache.add(object);
    // }
    //
    // // switch the current place to currentObjects list!
    // currentDeletedObjects.remove(object);
    // currentObjects.add(object);
    // object.setDeleleted(false);
    //
    // setRowCountFireListChange();
    //
    // }
    //
    // protected void setRowCountFireListChange() {
    // // setRowCount(isShowDeletedActive() ? currentDeletedObjects.size() :
    // currentObjects.size());
    // fireListChangedEvent();
    // }
    //
    // /**
    // * this method invoke callback.onRowsReady with the sublist of
    // getActuallyShownObjectsOnAllPages() 's result
    // *
    // * example: IneTable's PagingScrollTable use this method for handle paging
    // */
    // public void requestRows() {
    //
    // List<KeyValueObject> requestedRows = new ArrayList<KeyValueObject>();
    // List<KeyValueObject> listToSearch = getActuallyShownObjectsOnAllPages();
    //
    // // if (request.getStartRow() < listToSearch.size()) {
    // // int endRow = request.getStartRow() + request.getNumRows();
    // // endRow = Math.min(listToSearch.size(), endRow);
    // //
    // // requestedRows = listToSearch.subList(request.getStartRow(), endRow);
    // // }
    //
    // // callback.onRowsReady(new RowResponse(requestedRows));
    // }
    //
    // public Collection<KeyValueObject> getCreatedObjects() {
    // return createdObjectsCache.values();
    // }
    //
    // /**
    // * there are just modified fields in kvos
    // * @return
    // */
    // public Collection<KeyValueObject> getDeltaCreatedObjects() {
    // ArrayList<KeyValueObject> kvolist = new ArrayList<KeyValueObject>();
    // for(KeyValueObject kvo : createdObjectsCache.values()) {
    // kvolist.add(kvo.makeDeltaKVO());
    // }
    // return kvolist;
    // }
    //
    // public Collection<KeyValueObject> getEditedObjects() {
    // return editedObjectsCache;
    // }
    //
    // /**
    // * there are just modified fields in kvos
    // * @return
    // */
    // public Collection<KeyValueObject> getDeltaEditedObjects() {
    // ArrayList<KeyValueObject> kvolist = new ArrayList<KeyValueObject>();
    // for(KeyValueObject kvo : editedObjectsCache) {
    // kvolist.add(kvo.makeDeltaKVO());
    // }
    // return kvolist;
    // }
    //
    // public Collection<KeyValueObject> getDeletedObjects() {
    // return deletedObjectsCache;
    // }
    //
    // /**
    // * there are just ids in kvos
    // * @return
    // */
    // public Collection<KeyValueObject> getIdDeletedObjects() {
    // ArrayList<KeyValueObject> kvolist = new ArrayList<KeyValueObject>();
    // for(KeyValueObject kvo : deletedObjectsCache) {
    // kvolist.add(kvo.makeIdKVO());
    // }
    // return kvolist;
    // }
    //
    // public Collection<KeyValueObject> getUnDeletedObjects() {
    // return undeletedObjectsCache;
    // }
    //
    // /**
    // * return current objects or current deleted objects
    // * @return
    // */
    // public List<KeyValueObject> getActuallyShownObjectsOnAllPages() {
    // return isShowDeletedActive() ? currentDeletedObjects : currentObjects;
    // }
    //
    // /**
    // * do nothing
    // */
    // @Override
    // public void setSearchPatameters(KeyValueObject searchParameters) {
    // }

}
