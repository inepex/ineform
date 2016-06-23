package com.inepex.ineom.shared.assistedobject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;

public class AssistedObjectUtil {

    public static List<Long> getObjectIds(Collection<AssistedObject> objList) {
        List<Long> idList = new ArrayList<Long>();
        for (AssistedObject obj : objList) {
            idList.add(obj.getId());
        }
        return idList;
    }

    public static String getObjectIdsAsString(Collection<AssistedObject> objList) {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        for (AssistedObject obj : objList) {
            sb.append(obj.getId());
            if (i < objList.size() - 1)
                sb.append(";");
            i++;
        }
        return sb.toString();
    }

    public static String getObjectIdsAsStringWithCustomDelimeter(
        Collection<AssistedObject> objList,
        String delimeter) {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        for (AssistedObject obj : objList) {
            sb.append(obj.getId());
            if (i < objList.size() - 1)
                sb.append(delimeter);
            i++;
        }
        return sb.toString();
    }

    public static String getIdFieldAsString(Collection<AssistedObject> objList, String idDescName) {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        for (AssistedObject obj : objList) {
            sb.append(obj.getLongUnchecked(idDescName));
            if (i < objList.size() - 1)
                sb.append(";");
            i++;
        }
        return sb.toString();
    }

    public static String getFieldAsString(Collection<AssistedObject> objList, String descName) {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        for (AssistedObject obj : objList) {
            sb.append(obj.getStringUnchecked(descName));
            if (i < objList.size() - 1)
                sb.append(";");
            i++;
        }
        return sb.toString();
    }

    public static String getRelationIdFieldAsString(
        Collection<AssistedObject> objList,
        String keyOfRelation) {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        for (AssistedObject obj : objList) {
            sb.append(obj.getRelationUnchecked(keyOfRelation).getId());
            if (i < objList.size() - 1)
                sb.append(";");
            i++;
        }
        return sb.toString();
    }

    public static String createIdListString(Collection<Long> ids) {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        for (Long l : ids) {
            sb.append(l);
            if (i < ids.size() - 1)
                sb.append(";");
            i++;
        }

        return sb.toString();
    }

    public static List<Relation> mapObjectsToRelation(Collection<AssistedObject> objList) {
        return mapObjectsToRelation(objList, IFConsts.KEY_ID);

    }

    public static List<Relation> mapObjectsToRelation(
        Collection<AssistedObject> objList,
        String displayNameKey) {
        List<Relation> relations = new ArrayList<>();
        for (AssistedObject obj : objList) {
            relations.add(new Relation(obj.getStringUnchecked(displayNameKey), obj));
        }
        return relations;
    }

    public static List<Long> getObjectIds(IneList ineList) {
        List<Long> idList = new ArrayList<Long>();
        for (Relation rel : ineList.getRelationList()) {
            idList.add(rel.getId());
        }
        return idList;
    }

    public static AssistedObject getAoById(Long id, List<AssistedObject> objectList) {
        for (AssistedObject o : objectList) {
            if (o.getId().equals(id)) {
                return o;
            }
        }
        return null;
    }

    public static List<Long> getIdFieldList(List<AssistedObject> objectList, String fieldName) {
        List<Long> idList = new ArrayList<Long>();
        for (AssistedObject object : objectList) {
            if (object.getLong(fieldName) != null) {
                idList.add(object.getLong(fieldName));
            }
        }
        return idList;
    }

    public static
        List<AssistedObject>
        getRelationAoList(List<AssistedObject> objectList, String fieldName) {
        List<AssistedObject> relAoList = new ArrayList<AssistedObject>();
        for (AssistedObject object : objectList) {
            if (object.getRelation(fieldName) != null
                && object.getRelation(fieldName).getKvo() != null) {
                relAoList.add(object.getRelation(fieldName).getKvo());
            }
        }
        return relAoList;
    }
}
