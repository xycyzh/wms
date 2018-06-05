package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.domain.Product;
import cn.wolfcode.wms.query.ProductQueryObject;
import cn.wolfcode.wms.service.IBrandService;
import cn.wolfcode.wms.service.IProductService;
import cn.wolfcode.wms.util.RequiredPermission;
import cn.wolfcode.wms.util.UploadUtil;
import cn.wolfcode.wms.vo.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private IBrandService brandService;
    @Autowired
    private ServletContext ctx;

    @RequiredPermission("商品列表")
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") ProductQueryObject qo) {
        model.addAttribute("pageResult", productService.query(qo));
        model.addAttribute("brands", brandService.selectAll());
        return "product/list";
    }

    @RequestMapping("/selectList")
    public String selectList(Model model, @ModelAttribute("qo") ProductQueryObject qo) {
        model.addAttribute("pageResult", productService.query(qo));
        model.addAttribute("brands", brandService.selectAll());
        return "product/selectList";
    }

    @RequiredPermission("商品编辑")
    @RequestMapping("/input")
    public String input(Model model, Long id) {
        if (id != null) {
            model.addAttribute("product", productService.get(id));
        }
        model.addAttribute("brands", brandService.selectAll());
        return "product/input";
    }

    @RequiredPermission("商品保存/更新")
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public JSONResult saveOrUpdate(Product product, MultipartFile img) {
        try {
            //更新的时候,如果重新上传了图片,那么就删除之前的图片
            if (!StringUtils.isEmpty(img) && img.getSize() > 0 && !StringUtils.isEmpty(product.getImagePath())) {
                UploadUtil.deleteFile(ctx, product.getImagePath());
            }
            //保存图片
            if (!StringUtils.isEmpty(img) && img.getSize() > 0) {
                String upload = UploadUtil.upload(img, ctx.getRealPath("/upload"));
                product.setImagePath(upload);
            }
            Brand brand = brandService.get(product.getBrand_id());
            product.setBrandName(brand.getName());
            productService.saveOrUpdate(product);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("操作成功");
    }

    @RequiredPermission("商品删除")
    @RequestMapping("/delete")
    @ResponseBody
    public JSONResult delete(Long id, String imagePath) {
        try {
            //删除商品的时候把商品的图片一并删掉
            if (!StringUtils.isEmpty(imagePath)) {
                UploadUtil.deleteFile(ctx, imagePath);
            }
            productService.delete(id);
        } catch (Exception e) {
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("删除成功");
    }
}
