package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.query.OrderChartQueryObject;
import cn.wolfcode.wms.query.SaleChartQueryObject;
import cn.wolfcode.wms.service.IBrandService;
import cn.wolfcode.wms.service.IChartsService;
import cn.wolfcode.wms.service.IClientService;
import cn.wolfcode.wms.service.ISupplierService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/chart")
public class ChartsController {
    @Autowired
    private IChartsService chartsService;
    @Autowired
    private ISupplierService supplierService;
    @Autowired
    private IBrandService brandService;
    @Autowired
    private IClientService clientService;

    @RequestMapping("/order")
    public String orderChart(Model model, @ModelAttribute("qo") OrderChartQueryObject qo) {
        model.addAttribute("groups", OrderChartQueryObject.map);
        model.addAttribute("orderChart", chartsService.queryOrderCharts(qo));
        model.addAttribute("suppliers", supplierService.selectAll());
        model.addAttribute("brands", brandService.selectAll());
        return "/chart/order";
    }

    @RequestMapping("/sale")
    public String saleChart(Model model, @ModelAttribute("qo") SaleChartQueryObject qo) {
        model.addAttribute("groups", SaleChartQueryObject.map);
        model.addAttribute("saleChart", chartsService.querySaleCharts(qo));
        model.addAttribute("clients", clientService.selectAll());
        model.addAttribute("brands", brandService.selectAll());
        return "/chart/sale";
    }

    @RequestMapping("/saleChartByBar")
    public String saleChartByBar(Model model, @ModelAttribute("qo") SaleChartQueryObject qo) {
        List<Map<String, Object>> maps = chartsService.querySaleCharts(qo);
        List<Object> groupTypes = new ArrayList<>();
        List<Object> totalAmount = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            groupTypes.add(map.get("groupType"));
            totalAmount.add(map.get("totalAmount"));
        }
        model.addAttribute("groupTypes", JSON.toJSONString(groupTypes));
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("groupName", SaleChartQueryObject.map.get(qo.getGroupBy()));
        return "/chart/saleChartByBar";
    }

    @RequestMapping("/saleChartByPie")
    public String saleChartByPie(Model model, @ModelAttribute("qo") SaleChartQueryObject qo) {
        List<Map<String, Object>> maps = chartsService.querySaleCharts(qo);
        model.addAttribute("groupName", SaleChartQueryObject.map.get(qo.getGroupBy()));
        List<Object> groupTypes = new ArrayList<>();
        List<Object> datas = new ArrayList<>();
        BigDecimal maxAmount = BigDecimal.ZERO;
        for (Map<String, Object> map : maps) {
            BigDecimal totalAmount = (BigDecimal) map.get("totalAmount");
            if (totalAmount.compareTo(maxAmount) > 0) {
                maxAmount = totalAmount;
            }
            groupTypes.add(map.get("groupType"));
            Map<Object, Object> m = new HashMap<>();
            m.put("name", map.get("groupType"));
            m.put("value", totalAmount);
            datas.add(m);
        }
        model.addAttribute("totalAmount", JSON.toJSONString(datas));
        model.addAttribute("groupTypes", JSON.toJSONString(groupTypes));
        model.addAttribute("maxAmount", maxAmount);
        return "/chart/saleChartByPie";
    }
}
