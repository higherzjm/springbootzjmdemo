package com.zjm.kindsmodels.Adapter.example2;

import java.util.ArrayList;
import java.util.List;

public class DispatchServletMain {

    public static List<HandlerAdapter> handlerAdapters = new ArrayList<HandlerAdapter>();

    public DispatchServletMain() {
        handlerAdapters.add(new AnnotationHandlerAdapter());
        handlerAdapters.add(new HttpHandlerAdapter());
        handlerAdapters.add(new SimpleHandlerAdapter());
    }


    public void doDispatch() {

        //此处模拟SpringMVC从request取handler的对象，仅仅new出，可以出，               
        //不论实现何种Controller，适配器总能经过适配以后得到想要的结果
//      HttpController controller = new HttpController();  
//      AnnotationController controller = new AnnotationController();  
        SimpleController controller = new SimpleController();
        //得到对应适配器  
        HandlerAdapter adapter = getHandler(controller);
        //通过适配器执行对应的controller对应方法  
        adapter.handle(controller);

    }
    //获取控制器对应的适配器
    public HandlerAdapter getHandler(Controller controller) {
        for (HandlerAdapter adapter : this.handlerAdapters) {
            if (adapter.supports(controller)) {
                return adapter;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        new DispatchServletMain().doDispatch();
    }

}  