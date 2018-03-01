package com.saniya.lostandfound;

import com.saniya.lostandfound.Model.Item;
import com.saniya.lostandfound.Model.User;
import com.saniya.lostandfound.Repositories.ItemRepository;
import com.saniya.lostandfound.Repositories.RoleRepository;
import com.saniya.lostandfound.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

///Permit All/////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////

    @RequestMapping("/")
    public String homePage(Model model){
        return "Home";
    }

    @RequestMapping("/login")
    public String login(Model model){
        return "Login";
    }


    @GetMapping("/newuser")
    public String addUser(Model model){
        model.addAttribute("user", new User());
        return "Registration";
    }

    @PostMapping("/newuser")
    public String processUser(@Valid @ModelAttribute("user") User user, BindingResult result){
        if(result.hasErrors()){
            return "Registration";
        }
        user.addRole(roleRepository.findByRoleName("USER"));
        userRepository.save(user);
        return "redirect:/";
    }

    @RequestMapping("/viewlostitems")
    public String viewLostItems(Model model){
        model.addAttribute("items", itemRepository.findByStatus("Lost"));
        return "LostItems";
    }

    @RequestMapping("/viewlostclothes")
    public String viewLostClothes(Model model){
        model.addAttribute("items", itemRepository.findByStatus("Lost"));
        return "ViewLostClothes";
    }

    @RequestMapping("/viewlostpets")
    public String viewLostPets(Model model){
        model.addAttribute("items", itemRepository.findByStatus("Lost"));
        return "ViewLostPets";
    }

    @RequestMapping("/viewlostother")
    public String viewLostOther(Model model){
        model.addAttribute("items", itemRepository.findByStatus("Lost"));
        return "ViewLostOther";
    }

////User/////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////


    @RequestMapping("/viewmyitems")
    public String viewMyItems(Authentication auth, Model model){
        User user = userRepository.findByUsername(auth.getName());
        Set<User> users = new HashSet<>();
        users.add(user);
        model.addAttribute("items", itemRepository.findByUsers(users));
        return "UserItems";
    }

    @GetMapping("/addlostitem")
    public String addLostItem(Model model){
        model.addAttribute("item", new Item());
        return "LostItemForm";
    }

    @PostMapping("/addlostitem")
    public String processLostItem(Authentication auth, @Valid@ModelAttribute("item") Item item, BindingResult result){
        if(result.hasErrors()){
            return "LostItemForm";
        }
        if (item.getImage() == null){
            if(item.getCategory().equalsIgnoreCase("Clothes")){
                item.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcStPd4U_vJAYIkKvgflddEVasYwDbRzG-yGD4rj2D6UcXdUnbrBXQ");
            }
            if(item.getCategory().equalsIgnoreCase("Pets")){
                item.setImage("https://freeclipartimage.com//storage/upload/dog-clipart/dog-clipart-49.png");
            }
            if(item.getCategory().equalsIgnoreCase("Other")){
                item.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTzriEmPtgIZE4hBjEFDVLmvMf58CVbodeZ05I0VuCySIxrdWGtHA");
            }
        }
        itemRepository.save(item);
        User user = userRepository.findByUsername(auth.getName());
        user.addItem(item);
        userRepository.save(user);
        return "redirect:/viewlostitems";
    }

    @PostMapping("/addlostitemA")
    public String processLostItemA(@Valid@ModelAttribute("item") Item item, BindingResult result){
        if(result.hasErrors()){
            return "LostItemForm";
        }
        itemRepository.save(item);
        return "redirect:/viewlostitems";
    }

    @RequestMapping("/search")
    public String searchResults(HttpServletRequest request, Model model){
        String query = request.getParameter("search");
        if(query.equalsIgnoreCase("Clothes") || query.equalsIgnoreCase("Pets") || query.equalsIgnoreCase("Other")){
            model.addAttribute("items", itemRepository.findByCategory(query));
        } else{
            model.addAttribute("items", itemRepository.findByItemNameContains(query));
        }
        model.addAttribute("search", query);
        return "SearchResults";
    }


////Admin/////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////


    @RequestMapping("/viewfounditems")
    public String viewFoundItems(Model model){
        ///send model only items of type found
        model.addAttribute("items", itemRepository.findByStatus("Found"));
        return "FoundItems";
    }

    @RequestMapping("/itemfound/{id}")
    public String lostItemReturned(@PathVariable("id") long id){
        Item item = itemRepository.findById(id);
        item.itemFound();
        itemRepository.save(item);
        return "redirect:/viewfounditems";
    }

    @RequestMapping("/itemlost/{id}")
    public String itemLost(@PathVariable("id") long id){
        Item item = itemRepository.findById(id);
        item.itemLost();
        itemRepository.save(item);
        return "redirect:/viewlostitems";
    }


}
