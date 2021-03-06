package com.beanframework.core;

import com.beanframework.cms.domain.Site;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.core.csv.AddressCsv;
import com.beanframework.core.csv.CompanyCsv;
import com.beanframework.core.csv.ConfigurationCsv;
import com.beanframework.core.csv.CountryCsv;
import com.beanframework.core.csv.CronjobCsv;
import com.beanframework.core.csv.CurrencyCsv;
import com.beanframework.core.csv.CustomerCsv;
import com.beanframework.core.csv.DynamicFieldCsv;
import com.beanframework.core.csv.DynamicFieldSlotCsv;
import com.beanframework.core.csv.DynamicFieldTemplateCsv;
import com.beanframework.core.csv.EmployeeCsv;
import com.beanframework.core.csv.EnumerationCsv;
import com.beanframework.core.csv.ImexCsv;
import com.beanframework.core.csv.LanguageCsv;
import com.beanframework.core.csv.MediaCsv;
import com.beanframework.core.csv.MenuCsv;
import com.beanframework.core.csv.RegionCsv;
import com.beanframework.core.csv.SiteCsv;
import com.beanframework.core.csv.UserAuthorityCsv;
import com.beanframework.core.csv.UserGroupCsv;
import com.beanframework.core.csv.UserPermissionCsv;
import com.beanframework.core.csv.UserRightCsv;
import com.beanframework.core.csv.VendorCsv;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;
import com.beanframework.enumuration.domain.Enumeration;
import com.beanframework.imex.domain.Imex;
import com.beanframework.internationalization.domain.Country;
import com.beanframework.internationalization.domain.Currency;
import com.beanframework.internationalization.domain.Language;
import com.beanframework.internationalization.domain.Region;
import com.beanframework.media.domain.Media;
import com.beanframework.menu.domain.Menu;
import com.beanframework.user.domain.Address;
import com.beanframework.user.domain.Company;
import com.beanframework.user.domain.Customer;
import com.beanframework.user.domain.Employee;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.Vendor;

public interface CoreImportListenerConstants {

  public interface ConfigurationImport {
    public static final String TYPE = Configuration.class.getSimpleName();
    public static final String NAME = "Configuration";
    public static final int SORT = 10;
    public static final String DESCRIPTION = "Update/Remove Configuration Data";
    public static final Class<ConfigurationCsv> CLASS_CSV = ConfigurationCsv.class;
    public static final Class<Configuration> CLASS_ENTITY = Configuration.class;
  }

  public interface LanguageImport {
    public static final String TYPE = Language.class.getSimpleName();
    public static final String NAME = "Language";
    public static final int SORT = 20;
    public static final String DESCRIPTION = "Update/Remove Language Data";
    public static final Class<LanguageCsv> CLASS_CSV = LanguageCsv.class;
    public static final Class<Language> CLASS_ENTITY = Language.class;
  }

  public interface CurrencyImport {
    public static final String TYPE = Currency.class.getSimpleName();
    public static final String NAME = "Currency";
    public static final int SORT = 21;
    public static final String DESCRIPTION = "Update/Remove Currency Data";
    public static final Class<CurrencyCsv> CLASS_CSV = CurrencyCsv.class;
    public static final Class<Currency> CLASS_ENTITY = Currency.class;
  }

  public interface CountryImport {
    public static final String TYPE = Country.class.getSimpleName();
    public static final String NAME = "Country";
    public static final int SORT = 22;
    public static final String DESCRIPTION = "Update/Remove Country Data";
    public static final Class<CountryCsv> CLASS_CSV = CountryCsv.class;
    public static final Class<Country> CLASS_ENTITY = Country.class;
  }

  public interface RegionImport {
    public static final String TYPE = Region.class.getSimpleName();
    public static final String NAME = "Region";
    public static final int SORT = 23;
    public static final String DESCRIPTION = "Update/Remove Region Data";
    public static final Class<RegionCsv> CLASS_CSV = RegionCsv.class;
    public static final Class<Region> CLASS_ENTITY = Region.class;
  }

  public interface EnumerationImport {
    public static final String TYPE = Enumeration.class.getSimpleName();
    public static final String NAME = "Enumeration";
    public static final int SORT = 30;
    public static final String DESCRIPTION = "Update/Remove Enumeration Data";
    public static final Class<EnumerationCsv> CLASS_CSV = EnumerationCsv.class;
    public static final Class<Enumeration> CLASS_ENTITY = Enumeration.class;
  }

  public interface DynamicFieldImport {
    public static final String TYPE = DynamicField.class.getSimpleName();
    public static final String NAME = "Dynamic Field";
    public static final int SORT = 40;
    public static final String DESCRIPTION = "Update/Remove Dynamic Field Data";
    public static final Class<DynamicFieldCsv> CLASS_CSV = DynamicFieldCsv.class;
    public static final Class<DynamicField> CLASS_ENTITY = DynamicField.class;
  }

  public interface DynamicFieldSlotImport {
    public static final String TYPE = DynamicFieldSlot.class.getSimpleName();
    public static final String NAME = "Dynamic Field Slot";
    public static final int SORT = 41;
    public static final String DESCRIPTION = "Update/Remove Dynamic Field Slot Data";
    public static final Class<DynamicFieldSlotCsv> CLASS_CSV = DynamicFieldSlotCsv.class;
    public static final Class<DynamicFieldSlot> CLASS_ENTITY = DynamicFieldSlot.class;
  }

  public interface DynamicFieldTemplateImport {
    public static final String TYPE = DynamicFieldTemplate.class.getSimpleName();
    public static final String NAME = "Dynamic Field Template";
    public static final int SORT = 42;
    public static final String DESCRIPTION = "Update/Remove Dynamic Field Template Data";
    public static final Class<DynamicFieldTemplateCsv> CLASS_CSV = DynamicFieldTemplateCsv.class;
    public static final Class<DynamicFieldTemplate> CLASS_ENTITY = DynamicFieldTemplate.class;
  }

  public interface UserRightImport {
    public static final String TYPE = UserRight.class.getSimpleName();
    public static final String NAME = "User Right";
    public static final int SORT = 60;
    public static final String DESCRIPTION = "Update/Remove User Right Data";
    public static final Class<UserRightCsv> CLASS_CSV = UserRightCsv.class;
    public static final Class<UserRight> CLASS_ENTITY = UserRight.class;
  }

  public interface UserPermissionImport {
    public static final String TYPE = UserPermission.class.getSimpleName();
    public static final String NAME = "User Permission";
    public static final int SORT = 70;
    public static final String DESCRIPTION = "Update/Remove User Permission Data";
    public static final Class<UserPermissionCsv> CLASS_CSV = UserPermissionCsv.class;
    public static final Class<UserPermission> CLASS_ENTITY = UserPermission.class;
  }

  public interface UserGroupImport {
    public static final String TYPE = UserGroup.class.getSimpleName();
    public static final String NAME = "User Group";
    public static final int SORT = 80;
    public static final String DESCRIPTION = "Update/Remove User Group Data";
    public static final Class<UserGroupCsv> CLASS_CSV = UserGroupCsv.class;
    public static final Class<UserGroup> CLASS_ENTITY = UserGroup.class;
  }

  public interface UserAuthorityImport {
    public static final String TYPE = UserAuthority.class.getSimpleName();
    public static final String NAME = "User Authority";
    public static final int SORT = 90;
    public static final String DESCRIPTION = "Update/Remove User Authority Data";
    public static final Class<UserAuthorityCsv> CLASS_CSV = UserAuthorityCsv.class;
    public static final Class<UserAuthority> CLASS_ENTITY = UserAuthority.class;
  }

  public interface MenuImport {
    public static final String TYPE = Menu.class.getSimpleName();
    public static final String NAME = "Menu";
    public static final int SORT = 100;
    public static final String DESCRIPTION = "Update/Remove Menu Data";
    public static final Class<MenuCsv> CLASS_CSV = MenuCsv.class;
    public static final Class<Menu> CLASS_ENTITY = Menu.class;
  }

  public interface CronjobImport {
    public static final String TYPE = Cronjob.class.getSimpleName();
    public static final String NAME = "Cronjob";
    public static final int SORT = 110;
    public static final String DESCRIPTION =
        "Update/Remove Cronjob Data. This would stop and resume all active cronjobs.";
    public static final Class<CronjobCsv> CLASS_CSV = CronjobCsv.class;
    public static final Class<Cronjob> CLASS_ENTITY = Cronjob.class;
  }

  public interface CompanyImport {
    public static final String TYPE = Company.class.getSimpleName();
    public static final String NAME = "Company";
    public static final int SORT = 111;
    public static final String DESCRIPTION = "Update/Remove Company Data";
    public static final Class<CompanyCsv> CLASS_CSV = CompanyCsv.class;
    public static final Class<Company> CLASS_ENTITY = Company.class;
  }

  public interface AddressImport {
    public static final String TYPE = Address.class.getSimpleName();
    public static final String NAME = "Address";
    public static final int SORT = 112;
    public static final String DESCRIPTION = "Update/Remove Address Data";
    public static final Class<AddressCsv> CLASS_CSV = AddressCsv.class;
    public static final Class<Address> CLASS_ENTITY = Address.class;
  }

  public interface EmployeeImport {
    public static final String TYPE = Employee.class.getSimpleName();
    public static final String NAME = "Employee";
    public static final int SORT = 130;
    public static final String DESCRIPTION = "Update/Remove Employee Data";
    public static final Class<EmployeeCsv> CLASS_CSV = EmployeeCsv.class;
    public static final Class<Employee> CLASS_ENTITY = Employee.class;
  }

  public interface CustomerImport {
    public static final String TYPE = Customer.class.getSimpleName();
    public static final String NAME = "Customer";
    public static final int SORT = 131;
    public static final String DESCRIPTION = "Update/Remove Customer Data";
    public static final Class<CustomerCsv> CLASS_CSV = CustomerCsv.class;
    public static final Class<Customer> CLASS_ENTITY = Customer.class;
  }

  public interface VendorImport {
    public static final String TYPE = Vendor.class.getSimpleName();
    public static final String NAME = "Vendor";
    public static final int SORT = 132;
    public static final String DESCRIPTION = "Update/Remove Vendor Data";
    public static final Class<VendorCsv> CLASS_CSV = VendorCsv.class;
    public static final Class<Vendor> CLASS_ENTITY = Vendor.class;
  }

  public interface SiteImport {
    public static final String TYPE = Site.class.getSimpleName();
    public static final String NAME = "Site";
    public static final int SORT = 150;
    public static final String DESCRIPTION = "Update/Remove Site Data";
    public static final Class<SiteCsv> CLASS_CSV = SiteCsv.class;
    public static final Class<Site> CLASS_ENTITY = Site.class;
  }

  public interface MediaImport {
    public static final String TYPE = Media.class.getSimpleName();
    public static final String NAME = "Media";
    public static final int SORT = 160;
    public static final String DESCRIPTION = "Update/Remove Media Data";
    public static final Class<MediaCsv> CLASS_CSV = MediaCsv.class;
    public static final Class<Media> CLASS_ENTITY = Media.class;
  }

  public interface ImexImport {
    public static final String TYPE = Imex.class.getSimpleName();
    public static final String NAME = "Imex";
    public static final int SORT = Integer.MAX_VALUE;
    public static final String DESCRIPTION = "Update/Remove Import Export Data";
    public static final Class<ImexCsv> CLASS_CSV = ImexCsv.class;
    public static final Class<Imex> CLASS_ENTITY = Imex.class;
  }

}
