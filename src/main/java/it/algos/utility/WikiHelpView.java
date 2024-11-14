package it.algos.utility;


import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import it.algos.vbase.annotation.IView;
import it.algos.vbase.constant.Gruppo;
import it.algos.vbase.tree.MenuObject;
import it.algos.vbase.tree.TreeNode;
import it.algos.vbase.ui.view.HelpView;
import jakarta.annotation.security.PermitAll;

@PermitAll
@Route(value = "wikihelp")
@CssImport("./styles/help.css")
@IView(menuName = "Wiki", menuGroup = Gruppo.UTILITY, vaadin = VaadinIcon.INFO_CIRCLE)
public class WikiHelpView extends HelpView {




    public TreeNode createMenuHelp() {
        TreeNode treeNode = new TreeNode(new MenuObject("Help"));
        TreeNode foglia;
        TreeNode fogliaSub;

        foglia = new TreeNode(new MenuObject("Home page", VaadinIcon.HOME.create(), WikiHelpView.class));
        treeNode.addChild(foglia);

        foglia = new TreeNode(new MenuObject("Wiki"));
        fogliaSub = new TreeNode(new MenuObject("Comandi", "comandi.adoc"));
        foglia.addChild(fogliaSub);
        fogliaSub = new TreeNode(new MenuObject("Snippets", "snippets.txt"));
        foglia.addChild(fogliaSub);
        fogliaSub = new TreeNode(new MenuObject("Venerdì", "venerdi.txt"));
        foglia.addChild(fogliaSub);
        treeNode.addChild(foglia);

        return treeNode;
    }

}
