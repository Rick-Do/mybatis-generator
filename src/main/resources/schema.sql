CREATE TABLE  IF NOT EXISTS `data_config` (
    id INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255),
    author VARCHAR(255),
    parent_package VARCHAR(255),
    model_package VARCHAR(255),
    controller_package VARCHAR(255),
    service_package VARCHAR(255),
    entity_package VARCHAR(255),
    xml_package VARCHAR(255),
    mapper_package VARCHAR(255),
    table_prefix VARCHAR(255),
    table_suffix VARCHAR(255),
    include VARCHAR(255),
    exclude VARCHAR(255),
    spring_doc BOOLEAN,
    swagger_doc BOOLEAN,
    date_type VARCHAR(20),
    comment_date VARCHAR(20),
    controller_id INT,
    entity_id INT,
    mapper_id INT,
    service_id INT,
    PRIMARY KEY (id)
);

CREATE TABLE  IF NOT EXISTS `entity_config` (
  id INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255),
  super_class VARCHAR(255),
  disable_serial_version BOOLEAN,
  enable_lombok BOOLEAN,
  enable_column_constant BOOLEAN,
  enable_chain_model BOOLEAN,
  naming VARCHAR(255),
  column_naming VARCHAR(255),
  id_type VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE  IF NOT EXISTS `service_config` (
    id INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255),
    super_service_class VARCHAR(255),
    super_service_impl_class VARCHAR(255),
    format_service_file_name VARCHAR(255),
    format_service_impl_file_name VARCHAR(255),
    PRIMARY KEY (id)
);
CREATE TABLE  IF NOT EXISTS `mapper_config` (
    id INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255),
    super_class VARCHAR(255),
    enable_mapper_annotation BOOLEAN,
    enable_base_result_map BOOLEAN,
    enable_base_column_list BOOLEAN,
    format_mapper_file_name VARCHAR(255),
    format_xml_file_name VARCHAR(255),
    PRIMARY KEY (id)
);
CREATE TABLE  IF NOT EXISTS `controller_config` (
    id INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255),
    super_class VARCHAR(255),
    enable_hyphen_style BOOLEAN,
    enable_rest_style BOOLEAN,
    format_file_name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `memory`(
    id VARCHAR(50),
    mem_value VARCHAR(255),
    mem_key VARCHAR(255),
    remark VARCHAR(255),
    mem_type VARCHAR(50),
    time_unit VARCHAR(50),
    expire LONG,
    save_time LONG,
    PRIMARY KEY (id)
)