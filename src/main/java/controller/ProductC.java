package controller;

import model.Result;
import model.base.product.Product;
import model.price.PriceModel;
import model.product.ProdModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.base.ProductService;

/**
 * Created by ZH on 2015/8/14.
 */
@Controller
@RequestMapping("/product")
public class ProductC {
    @Autowired
    private ProductService productService;

    @RequestMapping("toadd")
    public ModelAndView toadd() {
        return new ModelAndView("base/product/add");
    }

    @RequestMapping("toList")
    public ModelAndView toList() {
        return new ModelAndView("base/product/list");
    }

    @RequestMapping("edit")
    public ModelAndView edit(@RequestParam("prodId") String prodId,@RequestParam("priceType") String priceType,Model model) {
        model.addAttribute("prodId",prodId);
        model.addAttribute("priceType",priceType);
        return new ModelAndView("base/product/add");
    }

    @RequestMapping(value = "addProduct")
    @ResponseBody
    public Result addProduct(Product product,PriceModel priceModel) {
        return productService.addProduct(product,priceModel);
    }
    @RequestMapping(value = "getProductList")
    @ResponseBody
    public Result getProductList(ProdModel prodModel) {
        return productService.getList(prodModel);
    }

    @RequestMapping(value = "editProduct")
    @ResponseBody
    public Result getProductById(ProdModel prodModel) {
        return productService.getProductById(prodModel);
    }
    @RequestMapping(value = "updateProduct")
    @ResponseBody
    public Result updateProduct(Product product,PriceModel priceModel) {
        return productService.updateProduct(product,priceModel);
    }
    @RequestMapping(value = "deleteProduct")
    @ResponseBody
    public Result deleteProduct(Product product,String priceType) {
        return productService.deleteProduct(product,priceType);
    }
}
