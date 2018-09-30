package com.example.demo;

import org.reflections.Reflections;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;


public class AnnoManageUtil {

    public static Map<String, ExecutorBean> getRequestMappingMethod(Class classes) {
        Map<String, ExecutorBean> mapp = new HashMap<>();
        Method[] methods = classes.getDeclaredMethods();
        RequestMapping requestMappingClass = (RequestMapping) classes.getAnnotation(RequestMapping.class);
        String classMappValue;
        try {
            String[] requestMappingClassValue = requestMappingClass.value();
            classMappValue = requestMappingClassValue[0];
        } catch (Exception e) {
            classMappValue = "";
        }
        for (Method method : methods) {
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
            if (null != requestMapping) {
                ExecutorBean executorBean = new ExecutorBean();
                try {
                    executorBean.setClassMapping(classMappValue);
                    executorBean.setRequestMethod(requestMapping.method());
                    executorBean.setName(requestMapping.name());
                    executorBean.setMethod(method);
                    executorBean.setPackageName(classes.
                            getPackage().getName());
                    executorBean.setClassName(classes.getSimpleName());
                } catch (Exception e) {
                    e.printStackTrace();

                }
                String key = Arrays.toString(requestMapping.value());
                if (StringUtils.isEmpty(key)) {
                    continue;
                }
                executorBean.setMethod(method);
                mapp.put(Arrays.toString(requestMapping.value()).replaceFirst("/", ""), executorBean);
            }
        }
        return sortMapByKey(mapp);
    }

    public static Map<String, ExecutorBean> sortMapByKey(Map<String, ExecutorBean> map) {
        Map<String, ExecutorBean> sortedMap = new TreeMap<>((keyl, key2) -> {
            int flag = keyl.compareTo(key2);
            int flag2 = 0;
            if (flag > 0) {
                flag2 = -1;
            } else if (flag < 0) {
                flag2 = 1;
            }
            return flag2;
        });
        sortedMap.putAll(map);
        return sortedMap;
    }

    public static Set<Class<?>> getPackageController(String packageName, boolean subPackageFlag) {
        Set<String> packageNametist = new HashSet<String>();
        packageNametist.add(packageName);
        if (subPackageFlag) {
            packageNametist.addAll(getPackageList(packageName));
        }
        Set<Class<?>> classesList = new HashSet<Class<?>>();
        for (String p : packageNametist) {
            classesList.addAll(getPackageController(p));
        }
        return classesList;
    }


    public static Set<Class<?>> getPackageController(String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> classeslist = reflections.getTypesAnnotatedWith(ApiDoc.class);
        return classeslist;
    }

    public static Class<?> getParamByMethod(Method method) {
        Set<Class<?>> paramNameSet = new HashSet<Class<?>>();
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (Class<?> clas : parameterTypes) {
            String parameterName = clas.getName();
            if (parameterName.contains("HttpServletRequest")) {
                continue;
            }
            paramNameSet.add(clas);
            return clas;
        }
        return null;
    }

    private static Set<String> getPackageByFile(String packagePath, Set<String> packageNameList) {
        File file = new File(packagePath);
        File[] childFiles = file.listFiles();
        if (null != childFiles) {
            for (
                    File childFile : childFiles) {
                if (childFile.isDirectory()) {
                    packageNameList.add(childFile.getAbsolutePath());
                    getPackageByFile(childFile.getAbsolutePath(), packageNameList);
                }
            }
        }
        return packageNameList;
    }

    public static Set<String> getPackageList(String packageName) {
        Set<String> fileNames = new HashSet<String>();
        ClassLoader loader =
                Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");
        URL url = loader.getResource(packagePath);
        if (url != null) {
            String type = url.getProtocol();
            if (type.equals("file")) {
                getPackageByFile(url.getPath(), fileNames);
            }
        }
        String path = url.getPath().replaceFirst("/", "").replace("/", ".");
        Set<String> filePathSet = new HashSet<String>();
        for (String str : fileNames) {
            str = str.replace("\\", ".");
            String packageEnd = str.replace(path, "");
            filePathSet.add(packageName + packageEnd);
        }
        return filePathSet;
    }

    public static List<String> getParamByClass(Class paramClass) {
        if (paramClass == null) {
            return new ArrayList<String>();
        }

        List<String> paramList = new ArrayList<>();
        Field[] fields = paramClass.getDeclaredFields();
        try {
            for (Field field : fields) {
                String fieldName = field.getName();
                if (fieldName.contains("serialVersionUlD")) {
                    continue;
                }
                if ((field.getModifiers() & java.lang.reflect.Modifier.STATIC) == java.lang.reflect.Modifier.STATIC) {
                    continue;
                }
                paramList.add(fieldName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramList;
    }

    public static List<ParamVO> getParamVOByClass(Class paramClass) {
        if (paramClass == null) {
            return new ArrayList<>();
        }

        List<ParamVO> paramVOList = new ArrayList<>();
        Field[] fields = paramClass.getDeclaredFields();
        try {
            for (Field field : fields) {
                String fieldName = field.getName();
                if (fieldName.contains("serialVersionUlD")) {
                    continue;
                }
                if ((field.getModifiers() & java.lang.reflect.Modifier.STATIC) == java.lang.reflect.Modifier.STATIC) {
                    continue;
                }
                ParamVO vo = new ParamVO();
                vo.setParamType(field.getType().getSimpleName());
                vo.setParamName(fieldName);
                paramVOList.add(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramVOList;
    }

    public static void print(String info, Object... args) {
        System.out.println(String.format(info, args));
    }

}