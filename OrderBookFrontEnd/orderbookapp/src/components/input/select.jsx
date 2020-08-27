import React from "react";

const Select = (props) => {
  return (
    <div className="form-group row container">
      <label htmlFor={props.name} className="m-2">
        {props.title}
      </label>
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
