package com.example.authserver.service;

import com.example.authserver.dao.OtpDao;
import com.example.authserver.dao.UserDao;
import com.example.authserver.ds.Otp;
import com.example.authserver.ds.User;
import com.example.authserver.util.GenerateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private OtpDao otpDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //  add user
    //  auth
    //  check
    //  renewOtp

    public void addUser(User user){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.save(user);
    }

    @Transactional
    public void renewOtp(User user){
        String code= GenerateCodeUtil.generateCode();

        Optional<Otp> userOtp =otpDao.findOtpByUsername(user.getUsername());

        if(userOtp.isPresent()){
            Otp otp=userOtp.get();
            otp.setCode(code);
//            otpDao.saveAndFlush(otp);
        }
        else{
            Otp otp=new Otp();
            otp.setUsername(user.getUsername());
            otp.setCode(code);

            otpDao.save(otp);
        }
    }

    @Transactional
    public void auth(User user){
        Optional<User> o=userDao.findUserByUsername(user.getUsername());

        if(o.isPresent()){
            User u=o.get();
            if(passwordEncoder.matches(user.getPassword(), u.getPassword())){
                renewOtp(user);
            }
            else{
                throw new BadCredentialsException("Bad Credential Exception !");
            }
        }
    }

    public boolean check(Otp otpToValidate){
        Optional<Otp> userOtp=otpDao.findOtpByUsername(otpToValidate.getUsername());

        if(userOtp.isPresent()){
            Otp otp=userOtp.get();

            if(otpToValidate.getCode().equals(otp.getCode())) {
                return true;
            }
        }
        return false;
    }
}
