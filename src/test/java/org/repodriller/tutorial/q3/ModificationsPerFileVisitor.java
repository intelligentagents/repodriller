package org.repodriller.tutorial.q3;

import org.repodriller.domain.Commit;
import org.repodriller.domain.Modification;
import org.repodriller.domain.ModificationType;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

import java.util.Hashtable;
import java.util.Map;

public class ModificationsPerFileVisitor implements CommitVisitor {

    private Map<String, Integer> files;

    public ModificationsPerFileVisitor() {
        this.files = new Hashtable<>();
    }

    @Override
    public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {

        for(Modification m : commit.getModifications()) {
            if(m.wasDeleted()) continue;

            else if(m.getType() == ModificationType.RENAME) {
                Integer total = files.get(m.getOldPath());
                files.remove(m.getOldPath());
                files.put(m.getNewPath(), total);
            }

            plusOne(m.getNewPath());
        }
    }

    public Map<String, Integer> getFiles() {
        return files;
    }

    private void plusOne(String file) {

        if(!files.containsKey(file))
            files.put(file, 0);

        Integer currentQty = files.get(file);
        files.put(file, currentQty + 1);

    }

    @Override
    public String name() {
        return "modifications-per-file";
    }
}
