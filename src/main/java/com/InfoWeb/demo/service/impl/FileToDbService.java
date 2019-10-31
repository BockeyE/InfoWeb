package com.InfoWeb.demo.service.impl;

import com.InfoWeb.demo.dao.JPA.FileDao;
import com.InfoWeb.demo.model.FileEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

/**
 * @author bockey
 */
@Service
public class FileToDbService {

    @Resource
    FileDao fileDao;

    public String saveFile(byte[] bytes, String name) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(name);
        fileEntity.setFileMD5(getSha256ByBytes(bytes));
        fileEntity.setFileBytes(bytes);
        FileEntity save = fileDao.save(fileEntity);
        if (save == null) {
            return null;
        }
        return save.getFileMD5();
    }

    public FileEntity getFileById(long fid) {
        Optional<FileEntity> byId = fileDao.findById(fid);
        return byId.get();
    }


    public String getMD5ByBytes(byte[] bytes) {
        return DigestUtils.md5DigestAsHex(bytes);
    }


    public FileEntity findByMD5(String md5) {
        List<FileEntity> fileMD5 = fileDao.findAll(new Specification<FileEntity>() {
            @Override
            public Predicate toPredicate(Root<FileEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                criteriaQuery.where(cb.and(cb.equal(root.get("fileMD5"), md5)));
                criteriaQuery.orderBy(cb.desc(root.get("id").as(Long.class)));
                return criteriaQuery.getRestriction();
            }
        });
        if (fileMD5.size() == 0) return null;
        return fileMD5.get(0);
    }

    public boolean isMD5Existed(String md5) {
        return findByMD5(md5) != null;
    }

    public static String getSHA256(String str) {
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodestr;
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    public static String getSha256ByBytes(byte[] bytes){
       return org.apache.commons.codec.digest.DigestUtils.sha256Hex(bytes);
    }

    public static void main(String[] args) throws IOException {
        File f=new File("c:/ZZBK/sqs.pdf");
        byte[] buf=new byte[(int) f.length()];
        FileInputStream fis=new FileInputStream(f);
        fis.read(buf);
        String s = byte2Hex(org.apache.commons.codec.digest.DigestUtils.sha256(buf));
        System.out.println(s);
        System.out.println(org.apache.commons.codec.digest.DigestUtils.sha256Hex("A"));
        System.out.println(s);
    }
}

