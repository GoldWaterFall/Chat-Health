package com.example.hospital.patient.wx.api.service.impl;

import cn.hutool.core.map.MapUtil;
import com.example.hospital.patient.wx.api.db.dao.MedicalDeptDao;
import com.example.hospital.patient.wx.api.service.MedicalDeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Shiqi
 */
@Service
public class MedicalDeptServiceImpl implements MedicalDeptService {
    @Resource
    private MedicalDeptDao medicalDeptDao;

    @Override
    public ArrayList<HashMap> searchMedicalDeptList(Map param) {
        ArrayList<HashMap> list = medicalDeptDao.searchMedicalDeptList(param);
        return list;
    }

    @Override
    public HashMap searchDeptAndSub() {
        //Check the list of departments and clinics
        ArrayList<HashMap> list = medicalDeptDao.searchDeptAndSub();
        //LinkedHashMap can record the order in which data is added
        LinkedHashMap map = new LinkedHashMap();

        for (HashMap one : list) {
            Integer deptId = MapUtil.getInt(one, "deptId");
            Integer subId = MapUtil.getInt(one, "subId");
            String deptName = MapUtil.getStr(one, "deptName");
            String subName = MapUtil.getStr(one, "subName");
            //Does the map contain the department of the current record?
            if (map.containsKey(deptName)) {
                //Get the list of clinics from the map
                ArrayList<HashMap> subList = (ArrayList<HashMap>) map.get(deptName);
                //Add clinic records to clinic list
                subList.add(new HashMap() {{
                    put("subId", subId);
                    put("subName", subName);
                }});
            }
            //There is no department currently recorded in the map
            else {
                //Create a department list and save it to map
                map.put(deptName, new ArrayList() {{
                    add(new HashMap() {{
                        put("subId", subId);
                        put("subName", subName);
                    }});
                }});
            }
        }
        return map;
    }
}
