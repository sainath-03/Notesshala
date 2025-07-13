    package com.example.notesshala.service;

    import com.example.notesshala.Model.Note;
    import com.example.notesshala.Model.User;
    import com.example.notesshala.repo.NoteRepository;
    import com.example.notesshala.repo.UserRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Service;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.nio.file.StandardOpenOption;
    import java.util.List;
    import java.util.NoSuchElementException;

    @Service
    public class NoteService {

        @Autowired
        private  NoteRepository noteRepo;

        @Autowired
        private UserRepository userRepo;

        @Value("${noteshala.upload-dir}")
        private String uploadDir;

//        public Note upload(Note metadata, MultipartFile file) throws IOException{
//            Path uploadPath = Paths.get(uploadDir);
//            Files.createDirectories(uploadPath);
//
//            Path target = uploadPath.resolve(file.getOriginalFilename());
//            Files.write(target,file.getBytes(), StandardOpenOption.CREATE);
//
//            metadata.setFilename((file.getOriginalFilename()));
//            return noteRepo.save(metadata);
//
//        }
        public void upload(Note metadata, MultipartFile file, String username) throws IOException {
            // 1. Find the user
            User owner = userRepo.findByUsername(username)
                    .orElseThrow(() -> new NoSuchElementException("User not found: " + username));
            metadata.setOwner(owner);

            // 2. Save file to disk
            saveFileToDisk(file);
            metadata.setFilename(file.getOriginalFilename());

            // 3. Persist metadata
            noteRepo.save(metadata);
        }

        private void saveFileToDisk(MultipartFile file) throws IOException {
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);
            Path target = uploadPath.resolve(file.getOriginalFilename());
            Files.write(target, file.getBytes(), StandardOpenOption.CREATE);
        }

        public List<Note> listByBranch(String branch) {
            return noteRepo.findByBranch(branch);
        }
        public Note getById(Long id) {
            return noteRepo.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Note not found: " + id));
        }
        public Path getFilePath(String filename) {
            return Paths.get(uploadDir).resolve(filename);
        }
        public List<Note> findByUsername(String username) {
            return noteRepo.findByOwnerUsername(username);
        }



    }
