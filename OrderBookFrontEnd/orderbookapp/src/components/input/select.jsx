import React from "react";

const Select = (props) => {
  return (
    <div className="form-group">
      <label htmlFor={props.name}>{props.title}</label>
      <br />
      <select name={props.name} value={props.value} onChange={props.onChange}>
        <option value="" disabled>
          {props.placeholder}
        </option>
        {props.options.map((option, index) => {
          return (
            <option key={index} value={option} label={option}>
              {option}
            </option>
          );
        })}
      </select>
    </div>
  );
};

export default Select;
