package com.zjm.kindsmodels.customermodels4_adapter;



import java.util.ArrayList;
import java.util.List;

public class HandlerUtil {
    public static List<IUniversityManage> handlerAdapters = new ArrayList<IUniversityManage>();
    public static IUniversityManage getHandler(IUniversityManage manage) {
        for (IUniversityManage universityManage : handlerAdapters) {
            if (universityManage.supports(manage)) {
                return universityManage;
            }
        }
        return null;
    }
}
