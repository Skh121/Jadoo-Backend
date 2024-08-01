package com.example.tourtravel.Service.Impl;


import com.example.tourtravel.Entity.About;
import com.example.tourtravel.Pojo.AboutPojo;
import com.example.tourtravel.Repo.AboutRepo;
import com.example.tourtravel.Service.AboutService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AboutServiceImpl implements AboutService {

    private final AboutRepo aboutRepo;

    @Override
    public void updateAbout(Integer id, AboutPojo aboutPojo) {
        if (aboutRepo.count() == 0) {
            throw new RuntimeException("No About entry exists to update");
        }
        About about = aboutRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("About with id " + id + " not found"));
        about.setOurMission(aboutPojo.getOurMission());
        about.setOurVision(aboutPojo.getOurVision());
        aboutRepo.save(about);
    }

    @Override
    public void deleteAbout(Integer id) {
        if (aboutRepo.count() == 0) {
            throw new RuntimeException("No About entry exists to delete");
        }
        if (aboutRepo.existsById(id)) {
            aboutRepo.deleteById(id);
        } else {
            throw new RuntimeException("About with id " + id + " not found");
        }
    }

    @Override
    public void createAbout(AboutPojo aboutPojo) {
        if (aboutRepo.count() > 0) {
            throw new RuntimeException("An About entry already exists");
        }
        About about = new About();
        about.setOurMission(aboutPojo.getOurMission());
        about.setOurVision(aboutPojo.getOurVision());
        aboutRepo.save(about);
    }

    @Override
    public AboutPojo getAbout(Integer id) {
        About about = aboutRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("About with id " + id + " not found"));
        return new AboutPojo(about.getAboutId(), about.getOurMission(), about.getOurVision());
    }

    @Override
    public List<AboutPojo> getAllAbout() {
        if (aboutRepo.count() == 0) {
            return List.of();
        }
        List<About> aboutList = aboutRepo.findAll();
        return aboutList.stream()
                .map(about -> new AboutPojo(about.getAboutId(), about.getOurMission(), about.getOurVision()))
                .toList();
    }
}
