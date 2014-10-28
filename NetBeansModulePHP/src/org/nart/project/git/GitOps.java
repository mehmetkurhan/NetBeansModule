/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.nart.project.git;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.internal.storage.file.PackFile;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.DepthWalk.RevWalk;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

/**
 *
 * @author Mehmet
 */
public class GitOps {
    
    private Repository repo;
    private Git git;
    private String remotePath;
    private String projectLoc;
    private String tmpPath;
    private File tempf;
    private UsernamePasswordCredentialsProvider credentials;
    private String absolutePath;
    
    public GitOps(String userName,String password,String url,String loc) throws IOException
    {
        tmpPath = System.getProperty("java.io.tmpdir") + "/Project"; 
        tempf = new File(tmpPath);
        remotePath = url ;
        credentials = new UsernamePasswordCredentialsProvider(userName,password);
        projectLoc = loc;
        repo = new FileRepository(tmpPath +"/.git");
        git = new Git(repo);
    }
    public GitOps(String path) throws IOException
    {
        absolutePath = path;
       File absfile = new File(absolutePath);
       repo = new FileRepository(absolutePath + "/.git");
        git = new Git(repo);
    }  
   public void cloneFilesToFolder() throws IOException
   {
       try
       {
      git.cloneRepository().setCredentialsProvider(credentials).setURI(remotePath).setDirectory(new File(tmpPath)).call(); 
      
      
       }
       catch(GitAPIException ex)
       {
           JOptionPane.showMessageDialog(null, "Exception occured at clonning process: " + ex.getMessage() );
       }
       finally
       {
           git.getRepository().close();
       }
   
   }
   public File getTempFile()
   {
       return tempf;
               
   }
   public List<String> listTags() throws GitAPIException {
        ArrayList<String> returnList = new ArrayList<String>();
        List<Ref> rlist = git.tagList().call();
        for(int i = 0; i < rlist.size();i++) {
            returnList.add(parse(rlist.get(i).getName()));           
        }
       
        return returnList;
        
       
   }
   private String parse(String tagName) {
       int start = tagName.lastIndexOf('/');
       return tagName.substring(start+1);
   }
   public void checkOut(String version) throws GitAPIException {
        git.checkout().setName(version).call();
       // git.getRepository().close();
        
   }
   public Repository getRepo()
   {
       return repo;
   }
   public Repository getTempRepo() throws IOException
   {
       repo = new FileRepository(tmpPath + "/.git");
       return repo;
   }
   public void setPath(String path)
   {
       this.projectLoc = path;
   }
  
}
  
 
    

