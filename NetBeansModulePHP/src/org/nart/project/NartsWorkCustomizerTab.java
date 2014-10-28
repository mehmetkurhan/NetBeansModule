/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.nart.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.nart.project.git.FileOps;
import org.nart.project.git.GitOps;
import org.nart.project.template.ConfigGenerator;
import org.nart.project.template.DatabaseGenerator;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;
import org.netbeans.spi.project.ui.support.ProjectCustomizer.Category;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

/**
 *
 * @author Çağatay
 */


public class NartsWorkCustomizerTab implements ProjectCustomizer.CompositeCategoryProvider {
    
  
    
   
    
    private final String name;
    private NartsWorkPanel panel;
    
    private GitOps gitops;
    private Category category;
    
    private String path;
 //   private String button_listener ;
    //private GitOps gitops;
    
   
    
    public NartsWorkCustomizerTab(String name) throws IOException {
        this.name = name;
        gitops = new GitOps(FileOps.getTempFile().getPath());
        
    }
   
    
    @Override
    public Category createCategory(Lookup lkp) {
        category = ProjectCustomizer.Category.create(name, name, null);
        category.setOkButtonListener(new OKOptionListener());
        category.setCloseListener(new CancelOptionListener());
        return category;
    }
    
    @Override
    public JComponent createComponent(Category ctgr, Lookup lkp) {
        Project activeProject = (Project) lkp.lookup(Project.class);
        path = activeProject.getProjectDirectory().getPath(); 
        panel = new NartsWorkPanel();
        panel.setPath(path);
        return panel;
    }
    public String getPath()
    {
        return path;
    }
    
    @NbBundle.Messages({"LBL_Config=Narts Work Configuration"})
    @ProjectCustomizer.CompositeCategoryProvider.Registration(
            projectType = "org-netbeans-modules-php-project",
            position  = 10)
    public static NartsWorkCustomizerTab createMyDemoConfigurationTab() throws IOException{
        // TODO change the method name
        return new NartsWorkCustomizerTab(Bundle.LBL_Config());
    }
    
    private class OKOptionListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent event) {
            
            category.setCloseListener(null);
          //  GitOps gitops = null;
            try {
                gitops = new GitOps(FileOps.getTempFile().getPath());
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
            if(panel.versionSelectcb.isVisible())
            {
            try {
                
               if(gitops != null)
               {
                gitops.checkOut(panel.getSelectedVersion());
                FileOps.copyFilesToActualFolder(FileOps.getTempFile(), new File(path));
                gitops.checkOut((String)gitops.listTags().get(0));
               //   JOptionPane.showMessageDialog(null, (String)gitops.listTags().get(0));
                gitops.getRepo().close();
                FileOps.deleteTemporaryFile(FileOps.getTempFile());
                gitops = null;
               }
            } 
            catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            } 
            
            }
        
    }
    }
    private class CancelOptionListener implements ActionListener
    {
        
        @Override
        public void actionPerformed(ActionEvent e) {
           
            
            if(gitops !=null && gitops.getRepo().getDirectory().exists()){
                try {
                    gitops.checkOut((String)gitops.listTags().get(0));
                 
               //     JOptionPane.showMessageDialog(null, gitops.listTags().get(0));
                     gitops.getRepo().close();
                } catch (GitAPIException ex) {
                    Exceptions.printStackTrace(ex);
                }
                
                FileOps.deleteTemporaryFile(FileOps.getTempFile());
               
            }
                  
        }
        
    }
    
}